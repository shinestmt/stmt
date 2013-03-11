# encoding=UTF-8

dist = {
        "fanhang" : 24,
        "laoban" : 23,
        "laoxie" : 24
    }
print type(dist)
print dist
print '-------------------------------------'

for x in dist.items():
    print x[0], x[1]


print '-------------------------------------'
data = { "%s:%s" % (x[0], x[1]) for x in dist.items() };
print type(data)
for x in data:
    print x
