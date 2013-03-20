import sys

def getnum(num, index):
    return str(num)[index-1:index]

if __name__ == '__main__':
    print sys.argv[1]
    print getnum(int(sys.argv[1]), int(sys.argv[2]))

