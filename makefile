# 312275746
# Naveh Marchoom

compile: bin
	find src -name "*.java" > sources.txt
	javac -d bin -cp biuoop-1.4.jar @sources.txt

jar:
	mkdir uber
	cd uber; jar -xf ../biuoop-1.4.jar; rm -rf META-INF
	jar cfm ass7game.jar Manifest.mf -C bin . -C resources . -C uber .
	rm -rf uber

run:
	java -cp biuoop-1.4.jar:resources:bin Ass7Game
	
bin:
	mkdir bin