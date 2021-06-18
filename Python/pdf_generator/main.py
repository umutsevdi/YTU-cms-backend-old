from src import put,get
from PIL import Image,ImageDraw,ImageFont
import pymongo
from pymongo import MongoClient
from src import put
from src import get
from bson.objectid import ObjectId
from fpdf import FPDF
from flask import Flask,send_file,request,abort
#from flask_cors import CORS
#"mongodb+srv://dbAdmin:qssUbEBV8Dh3cnUs@sat.6wtix.mongodb.net/myFirstDatabase?retryWrites=true&w=majority"
def main():
    try:
        club_id = ObjectId("607d9c2ab3812c5540b20368")  # ObjectId(request.args.get('club_id'))
        name = "Activity From Request"
        cluster = MongoClient(
            "mongodb+srv://dbAdmin:qssUbEBV8Dh3cnUs@sat.6wtix.mongodb.net/myFirstDatabase?retryWrites=true&w=majority")
        db = cluster["kulüpyonetimsistemi"]
        img = Image.open("sas.png")
        blank_page = Image.open("blank_page.png")
        collection_club = db["clubs"]
        result = collection_club.find_one({"_id": club_id})

        y_pix = put.club_name(img,result)
        y_pix = put.event_name(img, y_pix, result, name)
        y_pix = put.event_type(img, y_pix, result, name)
        y_pix = put.date(img, y_pix, result, name)
        y_pix = put.location(img, y_pix, result, name)
        y_pix = put.content(img, y_pix, result, name)

        y_pix = 480
        y_pix = put.speakers(blank_page, y_pix, result, name)
        y_pix = put.companies(blank_page, y_pix, result, name)
        put.sign(blank_page)
        width, height = img.size

        pdf = FPDF(unit="pt", format=[width, height])

        img.save("page1.png")
        blank_page.save("page2.png")

        pdf.add_page()
        pdf.image("page1.png", 0, 0)
        pdf.add_page()
        pdf.image("page2.png", 0, 0)
        pdf.output(str(get.club_name(result)+".pdf"), "F")

        img.close()
        blank_page.close()
        pdf.close()


    except:
        abort(404)
        pass

main()