primes.o: primes.c
	gcc -c -o primes.o primes.c

fuck.o: fuck.c primes.o
	gcc -c -o fuck.o fuck.c

main.o: main.c fuck.o
	gcc -c -o main.o main.c

OBJECTS = main.o fuck.o primes.o

result: $(OBJECTS) 
	gcc $(OBJECTS)

.PHONY: build, run, clean
build:
	make result
run:
	make build && ./a.out
clean:
	rm -rf ./*.out ./*.o

