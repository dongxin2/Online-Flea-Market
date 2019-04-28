import sys
list = sys.argv[1]
list1 = list[2:-2]
list3 = list1.split('],[')
# print(list)
descoutput = open('desc.txt','w')
funcoutput = open('func.txt','w')
for i in range(len(list3)):
    print(i)
    str1 = list3[i]
    output = str1.split(",  ")
    # print(output)
    row1 = output[0]
    row2 = output[1]
	# else: row2 = "null"
	# if(output[2]):
    row3 = output[2]
	# else: row3 = "null"
	# print(row1[1:])
    desctxt='{}.{}'.format(row1,row2)
    functxt='{}.{}'.format(row1[1:],row3[:-1])
	# print(type(rowtxt))
    descoutput.write(desctxt)
    funcoutput.write(functxt)
    descoutput.write('\n')
    funcoutput.write('\n')
descoutput.close()
funcoutput.close()
print("finish")
