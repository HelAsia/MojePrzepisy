from Logger import *
import json
from decimal import Decimal as D
import re


class Recipes:
    database = None

    def __init__(self):
        pass

    def __init__(self, database):
        self.database = database

    def setDatabase(self,database):
        self.database = database