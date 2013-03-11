# encoding=UTF-8

class Person:
    test = 0;

    def __init__(self, name):
        self.name = name
        test = 2
    def sayHi(self):
        print 'Hello, how are you?', self.name


p = Person('fan')
p.sayHi()