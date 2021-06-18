def club_name(result):
    return "Kulüp Adı: " + result["name"]
def event_name(result,name):
    return "Etkinkik Adı: " + result["activities"][name]["name"]
def event_type(result,name):
    return "Etkinlik Türü: " + result["activities"][name]["event_type"]
def start_date(result,name):
    return str(result["activities"]["Activity From Request"]["startDate"]).split()[0]
def end_date(result,name):
    return str(result["activities"]["Activity From Request"]["endDate"]).split()[0]
def location(result,name):
    return "Etkinliğin Yapılacağı yer: " + result["activities"][name]["location"]
def content(result,name):
    return "Etkinliğin İçeriği: " + result["activities"][name]["description"]
def speakers(result,name):
    return result["activities"][name]["speakers"]
def companies(result,name):
    return result["activities"][name]["companies"]




