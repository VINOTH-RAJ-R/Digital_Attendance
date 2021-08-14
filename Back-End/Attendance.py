import cv2
import numpy as np
import face_recognition
from datetime import datetime
import os
import pyrebase

config = {
'''paste your Firebase config lines'''
}
while 1:
    firebase = pyrebase.initialize_app(config)
    stroge = firebase.storage()
    path_on_cloud = "Attendance/image"
    stroge.child(path_on_cloud).download("Image attendance/new.jpeg")




    path = 'Images'
    images = []
    classNames = []
    myList = os.listdir(path)
    #print(myList)

    for cls in myList:
        curImg = cv2.imread(f'{path}/{cls}')
        images.append(curImg)
        classNames.append(os.path.splitext(cls)[0])
    #print(classNames)

    def findEncodings(images):
        encodelist = []
        for img in images:
            img = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
            encode = face_recognition.face_encodings(img)[0]
            encodelist.append(encode)
        return encodelist

    def markAttendance(name):
        with open('attendance.csv','r+') as f:
            mydataList = f.readline()
            nameList = []
            for line in mydataList:
                entry = line.split(',')
                nameList.append(entry[0])
            if name not in nameList:
                now = datetime.now()
                datstr = now.strftime('%H:%M:%S')
                pre = 'Present'
                f.writelines(f'\n{name},{datstr},{pre}')




    encodalistknown = findEncodings(images)




    j = 4
    while j<5:
        j=j+1
        img = cv2.imread("Image attendance/new.jpeg")


        facecur = face_recognition.face_locations(img)
        encode = face_recognition.face_encodings(img,facecur)


        for encodeface , faceloc in zip (encode,facecur):
            matches = face_recognition.compare_faces(encodalistknown,encodeface)
            print(matches)
            facedis = face_recognition.face_distance(encodalistknown,encodeface)
            matchIndex = np.argmin(facedis)


            if matches[matchIndex]:
                name = classNames[matchIndex].upper()
                print(name)
                y1,x2,y2,x1 = faceloc
                y1, x2, y2, x1 = y1*4,x2*4,y2*4,x1*4
                cv2.rectangle(img,(x1,y1),(x2,y2),(0,255,0),2)

                cv2.putText(img,name,(x1+6,y2-6),cv2.FONT_HERSHEY_COMPLEX,1,(0,0,255),0)
                markAttendance(name)

        path_on_cloud = "sheet/attendance.csv"
        stroge.child(path_on_cloud).put("attendance.csv")
        print('uploaded your attendance to firebase successfully')

        cv2.waitKey(1)
