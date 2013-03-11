# encoding=UTF-8

s = "中文ABC"
s1 = u"中文ABC"
s2 = unicode(s, "UTF-8") #省略参数将用python默认的ASCII来解码
s3 = s.decode("UTF-8") #把str转换成unicode是decode，unicode函数作用与之相同
print len(s)
print len(s1)
print len(s2)
print len(s3)
