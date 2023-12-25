input = open("input2.txt").read().split("\n")
t = 0
r = 0
for current in input :
	l, w, h = current.split("x")
	l = int(l)
	w = int(w)
	h = int(h)
	t += ((2 * l * w) + (2 * l * h) + (2 * w * h))
	t += min((l*h), (h*w), (l*w))

	r += l * w * h
	r += min((2*(l+h)), (2*(w+h)), (2*(l+w)))

#print(t)
print(r)