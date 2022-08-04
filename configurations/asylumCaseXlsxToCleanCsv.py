""" HRF Data Cleaning Script """
import uuid
from typing import Iterable
import pandas
from pandas import DataFrame


def read_excel(filepath: str) -> DataFrame:
    return pandas.read_excel(filepath)


def save_csv(filepath: str, data: DataFrame):
    data.to_csv(filepath)


def remove_headers(data: DataFrame, drop_list: Iterable[str]) -> DataFrame:
    return data.drop(columns=drop_list)


def rename_headers(data: DataFrame, column_names) -> DataFrame:
    return data.rename(columns=column_names)


def set_uuid_index(data: DataFrame) -> DataFrame:
    data["id"] = [uuid.uuid4() for _ in range(data.shape[0])]
    return data.set_index("id")


if __name__ == '__main__':
    column_names = {"asylum_office": "asylumOffice",
                    "citizenship": "citizenship", "case_outcome": "caseOutcome",
                    "completion_date": "completionDate", "data_current_as_of": "dateRecieved"}

    df = read_excel("/content/data.xlsx")
    df = set_uuid_index(df)
    df = remove_headers(df, ["affirmative_case_id", "race_or_ethnicity"])
    df = rename_headers(df, column_names)
    save_csv("asylum-cases-data.csv", df)