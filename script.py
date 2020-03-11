import os
import requests

file = "sha_save.txt"
myfile = open(file, mode='w', encoding='Latin_1')
os.system("git log --pretty=format:%H >> sha_save.txt")
myfile = open(file, mode='r', encoding='Latin_1')
for line in myfile:
    line = requests.get('https://api.github.com/repos/architecture-playground/playground-service/git/commits/' + line.strip())
    print(line.text)
