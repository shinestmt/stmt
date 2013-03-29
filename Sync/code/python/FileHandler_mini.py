#!/usr/bin/env python
import sys
import os
import re
#import pyinotify
import pyinotify.pyc
import datetime
 
if len(sys.argv) <2:
    print("\n  /*//////////////////////////////////////////");
    print("  //     Sov Webshell monitoring tool       //");
    print("  //      by:0x001  http://www.0x001.com    //");
    print("  //////////////////////////////////////////*/\n");
    print('  Usage : python SovMonitor.py /web/0x001\n')
    sys.exit()

pattern = re.compile(r"\bserver.execute\s+request|\bexecute\s+request|\beval\s+request|\beval_r\s+request|\bExecuteGlobal\s+request|\bExecute\s+Session|\bexecute\s*\(+\s*request|\beval\s*\(+\s*request|\beval_r\s*\(+\s*request|\bExecuteGlobal\s*\(+\s*request|\bExecute\s*\(+\s*Session|\s*'\s*:\s*eval|\bServer.CreateObject\s*\(\s*\"ScriptControl\"\s*\)|\bSystem.Reflection.Assembly.Load|\beval\s*\(+\s*\$|\beval_r\s*\(+\s*\$|\bassert\s*\(+\s*\$|`\$_Request\[.*`|`\$_GET\[.*`|`\$_POST\[.*`|\.ExecuteStatement\s*\(|\bnew\s+WebAdmin2Y|\beval\s*\(\s*@?base64_decode\s*\(|\beval\s*\(\s*@?gzuncompress\s*\(\s*@?base64_decode\(|\binclude.*(\.jpg|\.gif|\.png|\.bmp|\.txt)|\brequire_once.*(\.jpg|\.gif|\.png|\.bmp|\.txt)|\brequire.*(\.jpg|\.gif|\.png|\.bmp|\.txt)|\bexecute\s*\(+\s*\w+\s*\(+.*\s*\)|\bshell_exec\b|\bpassthru\s*\(|\bwscript\.shell\b|\bShell\.Application\b|\bVBScript\.Encode\b|\bxp_cmdshell\b|\bproc_open\b|\bSystem\.Net\.Sockets\b|\bSystem\.Diagnostics\b|\bSystem\.DirectoryServices\b|\bSystem\.ServiceProcess\b|\bnew\s+Socket\b|\bSystem\.Reflection\.Assembly\.Load\(Request\.BinaryRead\b|\bRuntime\.getRuntime\(\)\.exec\b|clsid:72C24DD5-D70A-438B-8A42-98424B88AFB8|clsid:13709620-C279-11CE-A49E-444553540000|clsid:0D43FE01-F093-11CF-8940-00A0C9054228|clsid:F935DC22-1CF0-11D0-ADB9-00C04FD58A0B|\bLANGUAGE\s*=\s*[\"]?\s*(vbscript|jscript|javascript).encode\b|'?e'?\.?'?v'?\.?'?a'?\.?'?l'")
p_file = re.compile(r"\.php$|\.java$|\.jpg$|\.gif$|\.png$|\.jpeg$")
 
def FileHandler(ev,filename):
    # find file www.2cto.com

    match = p_file.search(filename)
    if match == None:
        return False

    e = os.path.exists(filename)
    if e == False:
        return False

    # read file
    filecontent = ''
    file = open(filename)
    while True:
        lines = file.readlines(100000)
        if not lines:
            break
        for line in lines:
            filecontent = filecontent +line
    #print filecontent

    match = pattern.finditer(filecontent)
    mlist = list(match)
    num = len(mlist)
    if num >0:
        nowtime = datetime.datetime.now().strftime("%Y-%m-%d %H:%M")
        #message_body = ''
        message_body = ev + filename + '\n\nDangerous keywords :\n'
        print '\n',nowtime,' ',ev,filename,', Matching number : ',num,'\nMatching Result:  ',
        for m in mlist:
            print m.group(),
            message_body = message_body + ' | ' + m.group()
        print '\n'
    else:
        #print '\nFalse, ',ev,filename,'\n'
        pass


#==== Monitor ====#
wm = pyinotify.WatchManager()  # Watch Manager
mask = pyinotify.IN_CREATE | pyinotify.IN_MODIFY  # watched events

class EventHandler(pyinotify.ProcessEvent):
    def process_IN_CREATE(self, event):
        #print "Creating:", event.pathname
        FileHandler("Creating:",event.pathname)

    def process_IN_MODIFY(self, event):
        #print "Modify:", event.pathname
        FileHandler("Modify:",event.pathname)

handler = EventHandler()
notifier = pyinotify.Notifier(wm, handler)
wdd = wm.add_watch(sys.argv[1], mask, rec=True)

notifier.loop()
