import time

def checkIsInteger(x):
    try:
        int(x)
        return True
    except TypeError:
        return False


def normalizeTime(x):
    x = x.replace(" AM", "")
    x = x.replace(" PM", "")
    return x


def changeDateFormat(queryResult):
    for queryResultTime in queryResult:
        queryResultTime['Date'] = int(time.mktime(queryResultTime['Date'].timetuple()))

    return queryResult