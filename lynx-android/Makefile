
debug:
	cd lynx-module && yarn && yarn run build
	./gradlew assembleDebug --console plain

release:
	cd lynx-module && git clean -xdf && yarn && yarn run build
	./gradlew assembleRelease --console plain