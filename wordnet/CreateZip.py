#!/usr/bin/env python
import os
import zipfile

EXCEPTIONS = ["KdTreeGenerator.java", "KdTreeVisualizer.java", "NearestNeighborVisualizer.java", "RangeSearchVisualizer.java"]

def zipdir(root, path, ziph, files):
    # ziph is zipfile handle
    for file in files:
        ziph.write(os.path.join(root, file))

if __name__ == '__main__':
    dir = os.getcwd()
    dir = dir.split('\\')
    dir = dir[len(dir) - 1]
    name = dir.lower() + "-" + "submit" + ".zip"
    
    items = os.listdir(".")
    newlist = []
    for names in items:
        if names.endswith(".java"):
            MoveOn = False
            for exception in EXCEPTIONS:
                if(names == exception):
                    MoveOn = True
            
            if(MoveOn == False):
                newlist.append(names)
    print newlist
        
    zipf = zipfile.ZipFile(name, mode = 'w') 
    try:
        for file in newlist:
            zipf.write(file)
    finally:
        zipf.close()
    