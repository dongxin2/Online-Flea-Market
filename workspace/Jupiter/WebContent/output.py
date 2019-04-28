import sys
list = sys.argv[1]
list1 = list[2:-2]
list3 = list1.split('],[')
descoutput = open('desc.txt','w')
funcoutput = open('func.txt','w')
for i in range(0,len(list3)):

	# print(list3[i])
	output = list3[i].split(',')
	# print(len(output))
	# print row
	# print(output)
	# output=row.split(",")
	row1 = output[0]
	# if(output[1]):
	row2 = output[1]
	# else: row2 = "null"
	# if(output[2]):
	row3 = output[2]
	# else: row3 = "null"
	# print(row1[1:])
	# print(row2).sub("[\s+\.\!\/_,$%^*(+\"\']+|[+——！，。？、~@#￥%……&*（）：]+".decode("utf8"), "".decode("utf8"), s.decode("utf8"))
	# print(row3[:-1]).sub("[\s+\.\!\/_,$%^*(+\"\']+|[+——！，。？、~@#￥%……&*（）：]+".decode("utf8"), "".decode("utf8"), s.decode("utf8"))
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
