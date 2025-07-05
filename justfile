set shell := ["nu", "-c"]

run:
	./gradlew runclient

data: && fmt-json
	./gradlew rundata

build: data
	./gradlew build 

fmt-json:
	#!/bin/nu
	print "\nFormatting...\n"

	let files = fd | parse '{name}' | $in.name | where {|file| $file | str contains ".json"}

	for file in $files {
		let text = open $file | to json --tabs 1
		$text + "\n" | save -f $file
	}
