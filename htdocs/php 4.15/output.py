import sys
list = sys.argv[1]
list1 = list[2:-2]
list3 = list1.split('],[')
outputfile = open('data.txt','w')
for i in range(0,len(list3)):
	print(i)
	print list3[i]
	output = list3[i].split('","')
	# print row
	print output
	# output=row.split(",")
	row1 = output[0]
	row2 = output[1]
	row3 = output[2]
	print(row1[1])
	print(row2)
	print(row3[:-1])
	rowtxt='{}.{}.{}'.format(row1[1],row2,row3[:-1])
	# print(type(rowtxt))
	outputfile.write(rowtxt)
	outputfile.write('\n')
output.close()

