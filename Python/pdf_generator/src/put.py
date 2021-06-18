from src import get
from PIL import Image,ImageDraw,ImageFont


def event_type(img,y_pix,result,name):
    content = get.event_type(result,name)
    font = ImageFont.truetype("arial.ttf", size=40)
    d = ImageDraw.Draw(img)

    if font.getsize(content)[0] < 1950:
        y_pix = y_pix + 120
        d.text((250, y_pix), content, fill=(0, 0, 0), font=font)
    else:

        lines = list()
        lines.append("")
        contentlist = content.split(" ")
        i = 0
        j = 0
        lines[i] = contentlist[j]
        while j < len(contentlist):
            y_pix = y_pix + 120
            while font.getsize(lines[i])[0] < 1950 and j < len(contentlist):
                lines[i] = lines[i] + " " + contentlist[j]
                j += 1

            d.text((250, y_pix), lines[i], fill=(0, 0, 0), font=font)
            i += 1
            if (j < len(contentlist)):
                lines.append(contentlist[j])
                j += 1
    return y_pix
def location(img,y_pix,result,name):
    content = get.location(result,name)
    font = ImageFont.truetype("arial.ttf", size=40)
    d = ImageDraw.Draw(img)

    if font.getsize(content)[0] < 1950:
        y_pix = y_pix + 120
        d.text((250, y_pix), content, fill=(0, 0, 0), font=font)
    else:

        lines = list()
        lines.append("")
        contentlist = content.split(" ")
        i = 0
        j = 0
        lines[i] = contentlist[j]
        while j < len(contentlist):
            y_pix = y_pix + 120
            while font.getsize(lines[i])[0] < 1950 and j < len(contentlist):
                lines[i] = lines[i] + " " + contentlist[j]
                j += 1

            d.text((250, y_pix), lines[i], fill=(0, 0, 0), font=font)
            i += 1
            if (j < len(contentlist)):
                lines.append(contentlist[j])
                j += 1
    return y_pix


def content(img,y_pix,result,name):
    content = get.content(result,name)
    font = ImageFont.truetype("arial.ttf", size=40)
    d = ImageDraw.Draw(img)

    if font.getsize(content)[0] < 1950:
        y_pix = y_pix + 120
        d.text((250, y_pix), content, fill=(0, 0, 0), font=font)
    else:

        lines = list()
        lines.append("")
        contentlist = content.split(" ")
        i = 0
        j = 0
        lines[i] = contentlist[j]
        while j < len(contentlist):
            y_pix = y_pix + 120
            while font.getsize(lines[i])[0] < 1950 and j < len(contentlist):
                lines[i] = lines[i] + " " + contentlist[j]
                j += 1

            d.text((250, y_pix), lines[i], fill=(0, 0, 0), font=font)
            i += 1
            if (j < len(contentlist)):
                lines.append(contentlist[j])
                j += 1
    return y_pix


def content_len(img,y_pix,result,name):
    content = get.content(result,name)
    font = ImageFont.truetype("arial.ttf", size=40)
    d = ImageDraw.Draw(img)

    if font.getsize(content)[0] < 1950:
        y_pix = y_pix + 120

    else:

        lines = list()
        lines.append("")
        contentlist = content.split(" ")
        i = 0
        j = 0
        lines[i] = contentlist[j]
        while j < len(contentlist):
            y_pix = y_pix + 120
            while font.getsize(lines[i])[0] < 1950 and j < len(contentlist):
                lines[i] = lines[i] + " " + contentlist[j]
                j += 1


            i += 1
            if (j < len(contentlist)):
                lines.append(contentlist[j])
                j += 1
    return y_pix


def speakers(img,y_pix,result,name):
    contentlist = ["Konuşmacılar: "]
    contentlist2 = get.speakers(result,name)
    contentlist = contentlist + contentlist2
    font = ImageFont.truetype("arial.ttf", size=40)
    d = ImageDraw.Draw(img)

    for content in contentlist:

        if font.getsize(content)[0] < 1950:
            y_pix = y_pix + 120
            d.text((250, y_pix), content, fill=(0, 0, 0), font=font)
        else:

            lines = list()
            lines.append("")
            contentlist = content.split(" ")
            i = 0
            j = 0
            lines[i] = contentlist[j]
            while j < len(contentlist):
                y_pix = y_pix + 120
                while font.getsize(lines[i])[0] < 1950 and j < len(contentlist):
                    lines[i] = lines[i] + " " + contentlist[j]
                    j += 1

                d.text((250, y_pix), lines[i], fill=(0, 0, 0), font=font)
                i += 1
                if (j < len(contentlist)):
                    lines.append(contentlist[j])
                    j += 1
    return y_pix

def speakers_len(img,y_pix,result,name):
    contentlist = ["Konuşmacılar: "]
    contentlist2 = get.speakers(result,name)
    contentlist = contentlist + contentlist2
    font = ImageFont.truetype("arial.ttf", size=40)
    d = ImageDraw.Draw(img)

    for content in contentlist:

        if font.getsize(content)[0] < 1950:
            y_pix = y_pix + 120

        else:

            lines = list()
            lines.append("")
            contentlist = content.split(" ")
            i = 0
            j = 0
            lines[i] = contentlist[j]
            while j < len(contentlist):
                y_pix = y_pix + 120
                while font.getsize(lines[i])[0] < 1950 and j < len(contentlist):
                    lines[i] = lines[i] + " " + contentlist[j]
                    j += 1


                i += 1
                if (j < len(contentlist)):
                    lines.append(contentlist[j])
                    j += 1
    return y_pix


def companies(img,y_pix,result,name):
    contentlist = ["Davetli Firmalar: "]
    contentlist2 = get.companies(result,name)
    contentlist = contentlist + contentlist2
    font = ImageFont.truetype("arial.ttf", size=40)
    d = ImageDraw.Draw(img)

    for content in contentlist:

        if font.getsize(content)[0] < 1950:
            y_pix = y_pix + 120
            d.text((250, y_pix), content, fill=(0, 0, 0), font=font)
        else:

            lines = list()
            lines.append("")
            contentlist = content.split(" ")
            i = 0
            j = 0
            lines[i] = contentlist[j]
            while j < len(contentlist):
                y_pix = y_pix + 120
                while font.getsize(lines[i])[0] < 1950 and j < len(contentlist):
                    lines[i] = lines[i] + " " + contentlist[j]
                    j += 1

                d.text((250, y_pix), lines[i], fill=(0, 0, 0), font=font)
                i += 1
                if (j < len(contentlist)):
                    lines.append(contentlist[j])
                    j += 1
    return y_pix


def companies_len(img,y_pix,result,name):
    contentlist = ["Davetli Firmalar:"]
    contentlist2 = get.companies(result,name)
    contentlist = contentlist + contentlist2
    font = ImageFont.truetype("arial.ttf", size=40)
    d = ImageDraw.Draw(img)

    for content in contentlist:

        if font.getsize(content)[0] < 1950:
            y_pix = y_pix + 120

        else:

            lines = list()
            lines.append("")
            contentlist = content.split(" ")
            i = 0
            j = 0
            lines[i] = contentlist[j]
            while j < len(contentlist):
                y_pix = y_pix + 120
                while font.getsize(lines[i])[0] < 1950 and j < len(contentlist):
                    lines[i] = lines[i] + " " + contentlist[j]
                    j += 1


                i += 1
                if (j < len(contentlist)):
                    lines.append(contentlist[j])
                    j += 1
    return y_pix


def event_name(img,y_pix,result,name):
    content = get.event_name(result,name)
    font = ImageFont.truetype("arial.ttf", size=40)
    d = ImageDraw.Draw(img)

    if font.getsize(content)[0] < 1950:
        y_pix = y_pix + 120
        d.text((250, y_pix), content, fill=(0, 0, 0), font=font)
    else:

        lines = list()
        lines.append("")
        contentlist = content.split(" ")
        i = 0
        j = 0
        lines[i] = contentlist[j]
        while j < len(contentlist):
            y_pix = y_pix + 120
            while font.getsize(lines[i])[0] < 1950 and j < len(contentlist):
                lines[i] = lines[i] + " " + contentlist[j]
                j += 1

            d.text((250, y_pix), lines[i], fill=(0, 0, 0), font=font)
            i += 1
            if (j < len(contentlist)):
                lines.append(contentlist[j])
                j += 1
    return y_pix


def date(img,y_pix,result,name):
    content = f"Başlangıç Tarihi / Bitiş Tarihi: " +get.start_date(result,name) + "/" + get.end_date(result,name)
    font = ImageFont.truetype("arial.ttf", size=40)
    d = ImageDraw.Draw(img)

    if font.getsize(content)[0] < 1950:
        y_pix = y_pix + 120
        d.text((250, y_pix), content, fill=(0, 0, 0), font=font)
    else:

        lines = list()
        lines.append("")
        contentlist = content.split(" ")
        i = 0
        j = 0
        lines[i] = contentlist[j]
        while j < len(contentlist):
            y_pix = y_pix + 120
            while font.getsize(lines[i])[0] < 1950 and j < len(contentlist):
                lines[i] = lines[i] + " " + contentlist[j]
                j += 1

            d.text((250, y_pix), lines[i], fill=(0, 0, 0), font=font)
            i += 1
            if (j < len(contentlist)):
                lines.append(contentlist[j])
                j += 1
    return y_pix


def club_name(img,result):
    content = get.club_name(result)
    font = ImageFont.truetype("arial.ttf", size=40)
    d = ImageDraw.Draw(img)

    if font.getsize(content)[0] < 1950:
        y_pix = 950
        d.text((250, y_pix), content, fill=(0, 0, 0), font=font)
    else:

        lines = list()
        lines.append("")
        contentlist = content.split(" ")
        i = 0
        j = 0
        lines[i] = contentlist[j]
        while j < len(contentlist):
            y_pix = 950
            while font.getsize(lines[i])[0] < 1950 and j < len(contentlist):
                lines[i] = lines[i] + " " + contentlist[j]
                j += 1

            d.text((250, y_pix), lines[i], fill=(0, 0, 0), font=font)
            i += 1
            if (j < len(contentlist)):
                lines.append(contentlist[j])
                j += 1
    return y_pix

def sign(img):
    content = "Spor Kültür ve Sağlık Daire Başkanlığı:\n\n\nEtkinliği onaylayan Kişinin Adı:\n\n\nİmza:"
    font = ImageFont.truetype("arial.ttf", size=40)
    d = ImageDraw.Draw(img)
    y_pix = 3000
    d.text((250, y_pix), content, fill=(0, 0, 0), font=font)
