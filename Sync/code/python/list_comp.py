# encoding=UTF-8

listone = [2, 3, 4]

listtwo = [2*i for i in listone if i > 2]
print listtwo

print ["aaaa".count(ch) for ch in 'aa']
a = "樊航abc"
print len(unicode(a, "UTF-8"))

aa = 'aaaaaaaaaaaa'
print "Table Editor: set syntax to '{0}' '{1}'".format(aa, aa)