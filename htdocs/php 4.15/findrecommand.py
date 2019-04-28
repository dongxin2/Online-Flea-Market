outputfile = open('recommand.txt')
lines = outputfile.readlines()
for line in lines:
	line = line.strip('\n')
	row = line.split(" ")
	# print(type(row))
	print row
