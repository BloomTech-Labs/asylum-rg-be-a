package com.bloomtech.asylumrgbea.controllers;

import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import com.bloomtech.asylumrgbea.controllers.exceptions.AsylumCaseNotFoundException;
import com.bloomtech.asylumrgbea.controllers.exceptions.BadRequestException;
import com.bloomtech.asylumrgbea.controllers.exceptions.PageNotFoundException;
import com.bloomtech.asylumrgbea.models.CasesQueryParameterDto;
import com.bloomtech.asylumrgbea.models.PageResponseDto;
import com.bloomtech.asylumrgbea.services.AsylumCaseService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class AsylumCaseControllerTest {
    private MockMvc mockMvc;
    @Captor
    private ArgumentCaptor<CasesQueryParameterDto> queryParamCaptor;
    @MockBean
    private AsylumCaseService service;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setup() {
        mockMvc             = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        queryParamCaptor    = ArgumentCaptor.forClass(CasesQueryParameterDto.class);
    }

    @AfterEach
    void teardown() {
        mockMvc             = null;
        queryParamCaptor    = null;
    }

    @Test
    void getCasesEndpoint_withNoQueryParameters_generatesEmptyQueryParameterDto() throws Exception {
        // GIVEN
        int defaultPage     = 1;
        int defaultLimit    = 10;

        when(service.getCasesBy(any(CasesQueryParameterDto.class))).thenReturn(null);

        // WHEN
        mockMvc.perform(get("/cases"))
                .andExpect(status().isOk());

        // THEN
        verify(service).getCasesBy(queryParamCaptor.capture());

        assertEquals(defaultPage,   queryParamCaptor.getValue().getPage());
        assertEquals(defaultLimit,  queryParamCaptor.getValue().getLimit());

        assertNull(queryParamCaptor.getValue().getCitizenship());
        assertNull(queryParamCaptor.getValue().getOutcome());
        assertNull(queryParamCaptor.getValue().getFrom());
        assertNull(queryParamCaptor.getValue().getTo());
        assertNull(queryParamCaptor.getValue().getOffice());
    }

    @Test
    void getCasesEndpoint_withOneParameter_generatesQueryParameterDto() throws Exception {
        // GIVEN
        int expectedPage = 99999;
        int defaultLimit = 10;

        when(service.getCasesBy(any(CasesQueryParameterDto.class))).thenReturn(new PageResponseDto());

        // WHEN
        mockMvc.perform(get("/cases")
                        .param("page", Integer.toString(expectedPage)))
                .andExpect(status().isOk())
                .andReturn();

        // THEN
        verify(service).getCasesBy(queryParamCaptor.capture());

        assertEquals(expectedPage,  queryParamCaptor.getValue().getPage());
        assertEquals(defaultLimit,  queryParamCaptor.getValue().getLimit());

        assertNull(queryParamCaptor.getValue().getCitizenship());
        assertNull(queryParamCaptor.getValue().getOutcome());
        assertNull(queryParamCaptor.getValue().getFrom());
        assertNull(queryParamCaptor.getValue().getTo());
        assertNull(queryParamCaptor.getValue().getOffice());
    }

    @Test
    void getCasesEndpoint_withTwoParameter_generatesQueryParameterDto() throws Exception {
        // GIVEN
        int expectedPage    = 99999;
        int expectedLimit   = 99999;

        when(service.getCasesBy(any(CasesQueryParameterDto.class))).thenReturn(new PageResponseDto());

        // WHEN
        mockMvc.perform(get("/cases")
                        .param("page",  Integer.toString(expectedPage))
                        .param("limit", Integer.toString(expectedLimit)))
                .andExpect(status().isOk());

        // THEN
        verify(service).getCasesBy(queryParamCaptor.capture());

        assertEquals(expectedPage,  queryParamCaptor.getValue().getPage());
        assertEquals(expectedLimit, queryParamCaptor.getValue().getLimit());

        assertNull(queryParamCaptor.getValue().getCitizenship());
        assertNull(queryParamCaptor.getValue().getOutcome());
        assertNull(queryParamCaptor.getValue().getFrom());
        assertNull(queryParamCaptor.getValue().getTo());
        assertNull(queryParamCaptor.getValue().getOffice());
    }

    @Test
    void getCasesEndpoint_withMultipleParameters_generatesQueryParameterDto() throws Exception {
        // GIVEN
        int expectedPage    = 1;
        int expectedLimit   = 10;
        String expectedCitizenship = "citizenshipA0citizenshipB0citizenshipC";
        String expectedOutcome = "outcomeA,outcomeB";
        String expectedFrom = "fromD";
        String expectedTo = "toD";
        String expectedOffice = "officeA,officeB";

        when(service.getCasesBy(any(CasesQueryParameterDto.class))).thenReturn(new PageResponseDto());

        // WHEN
        mockMvc.perform(get("/cases")
                        .param("citizenship",   expectedCitizenship)
                        .param("outcome",       expectedOutcome)
                        .param("from",          expectedFrom)
                        .param("to",            expectedTo)
                        .param("office",        expectedOffice))
                .andExpect(status().isOk());

        // THEN
        verify(service).getCasesBy(queryParamCaptor.capture());

        assertEquals(expectedPage,          queryParamCaptor.getValue().getPage());
        assertEquals(expectedLimit,         queryParamCaptor.getValue().getLimit());
        assertEquals(expectedCitizenship,   queryParamCaptor.getValue().getCitizenship());
        assertEquals(expectedOutcome,       queryParamCaptor.getValue().getOutcome());
        assertEquals(expectedFrom,          queryParamCaptor.getValue().getFrom());
        assertEquals(expectedTo,            queryParamCaptor.getValue().getTo());
        assertEquals(expectedOffice,        queryParamCaptor.getValue().getOffice());
    }

    @Test
    void getCasesEndpoint_withQueryParametersThatResultEmpty_generatesErrorDto() throws Exception {
        // GIVEN
        int expectedPage    = 99999;
        int expectedLimit   = 99999;
        String expectedCitizenship  = "citizenshipA";
        String expectedOutcome      = "outcomeZ";
        String expectedValue        = "Test Message: A";
        String expectedKey          = "message";

        when(service.getCasesBy(any(CasesQueryParameterDto.class)))
                .thenThrow(new AsylumCaseNotFoundException(expectedValue));

        // WHEN
        MvcResult actual = mockMvc.perform(get("/cases")
                        .param("page",          Integer.toString(expectedPage))
                        .param("limit",         Integer.toString(expectedLimit))
                        .param("citizenship",   expectedCitizenship)
                        .param("outcome",       expectedOutcome))
                .andExpect(status().is4xxClientError())
                .andReturn();

        // THEN
        verify(service).getCasesBy(queryParamCaptor.capture());

        assertEquals(expectedPage,          queryParamCaptor.getValue().getPage());
        assertEquals(expectedLimit,         queryParamCaptor.getValue().getLimit());
        assertEquals(expectedCitizenship,   queryParamCaptor.getValue().getCitizenship());
        assertEquals(expectedOutcome,       queryParamCaptor.getValue().getOutcome());

        assertNull(queryParamCaptor.getValue().getFrom());
        assertNull(queryParamCaptor.getValue().getTo());
        assertNull(queryParamCaptor.getValue().getOffice());

        assertTrue(actual.getResponse().getContentAsString().contains(expectedValue));
        assertTrue(actual.getResponse().getContentAsString().contains(expectedKey));
    }

    @Test
    void getCasesEndpoint_withLimitAndPageParametersThatResultEmpty_generatesErrorDto() throws Exception {
        // GIVEN
        int expectedPage    = 99999;
        int expectedLimit   = 99999;
        String expectedValue    = "Test Message: B";
        String expectedKey      = "message";

        when(service.getCasesBy(any(CasesQueryParameterDto.class)))
                .thenThrow(new PageNotFoundException(expectedValue));

        // WHEN
        MvcResult actual = mockMvc.perform(get("/cases")
                        .param("page",  Integer.toString(expectedPage))
                        .param("limit", Integer.toString(expectedLimit)))
                .andExpect(status().is4xxClientError())
                .andReturn();

        // THEN
        verify(service).getCasesBy(queryParamCaptor.capture());

        assertEquals(expectedPage,  queryParamCaptor.getValue().getPage());
        assertEquals(expectedLimit, queryParamCaptor.getValue().getLimit());

        assertNull(queryParamCaptor.getValue().getCitizenship());
        assertNull(queryParamCaptor.getValue().getOutcome());
        assertNull(queryParamCaptor.getValue().getFrom());
        assertNull(queryParamCaptor.getValue().getTo());
        assertNull(queryParamCaptor.getValue().getOffice());

        assertTrue(actual.getResponse().getContentAsString().contains(expectedValue));
        assertTrue(actual.getResponse().getContentAsString().contains(expectedKey));
    }

    @Test
    void getCasesEndpoint_withInvalidLimitAndPageParameters_generatesErrorDto() throws Exception {
        // GIVEN
        int expectedPage    = -1;
        int expectedLimit   = 0;
        String expectedValue    = "Test Message: C";
        String expectedKey      = "message";

        when(service.getCasesBy(any(CasesQueryParameterDto.class)))
                .thenThrow(new BadRequestException(expectedValue));

        // WHEN
        MvcResult actual = mockMvc.perform(get("/cases")
                        .param("page",  Integer.toString(expectedPage))
                        .param("limit", Integer.toString(expectedLimit)))
                .andExpect(status().is4xxClientError())
                .andReturn();

        // THEN
        verify(service).getCasesBy(queryParamCaptor.capture());

        assertEquals(expectedPage,  queryParamCaptor.getValue().getPage());
        assertEquals(expectedLimit, queryParamCaptor.getValue().getLimit());
        assertNull(queryParamCaptor.getValue().getCitizenship());
        assertNull(queryParamCaptor.getValue().getOutcome());
        assertNull(queryParamCaptor.getValue().getFrom());
        assertNull(queryParamCaptor.getValue().getTo());
        assertNull(queryParamCaptor.getValue().getOffice());

        assertTrue(actual.getResponse().getContentAsString().contains(expectedValue));
        assertTrue(actual.getResponse().getContentAsString().contains(expectedKey));
    }

    @Test
    void getCasesEndpoint_AmazonDynamoDBExceptionThrown_generatesErrorDto() throws Exception {
        // GIVEN
        int defaultPage     = 1;
        int defaultLimit    = 10;
        String expectedValue = "Error: Cannot retrieve cases from database...";
        String expectedKey = "message";

        when(service.getCasesBy(any(CasesQueryParameterDto.class)))
                .thenThrow(new AmazonDynamoDBException(expectedValue));

        // WHEN
        MvcResult actual = mockMvc.perform(get("/cases")
                        .param("page",  Integer.toString(defaultPage))
                        .param("limit", Integer.toString(defaultLimit)))
                .andExpect(status().is5xxServerError())
                .andReturn();

        // THEN
        verify(service).getCasesBy(queryParamCaptor.capture());

        assertEquals(defaultPage,  queryParamCaptor.getValue().getPage());
        assertEquals(defaultLimit, queryParamCaptor.getValue().getLimit());
        assertNull(queryParamCaptor.getValue().getCitizenship());
        assertNull(queryParamCaptor.getValue().getOutcome());
        assertNull(queryParamCaptor.getValue().getFrom());
        assertNull(queryParamCaptor.getValue().getTo());
        assertNull(queryParamCaptor.getValue().getOffice());

        System.out.println(actual.getResponse().getContentAsString());

        assertTrue(actual.getResponse().getContentAsString().contains(expectedValue));
        assertTrue(actual.getResponse().getContentAsString().contains(expectedKey));
    }
}
