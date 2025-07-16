

lynx-build:
	cd lynx-module && yarn && yarn run build

lynx-build-release:
	cd lynx-module && git clean -xdf && yarn && yarn run build

installDebug: lynx-build
	./gradlew installDebug --console plain

release: lynx-build-release
	./gradlew assembleRelease --console plain