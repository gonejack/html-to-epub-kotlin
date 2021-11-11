# html-to-epub-kotlin

This command line converts .html file to .epub.

[![GitHub license](https://img.shields.io/github/license/gonejack/html-to-epub-kotlin.svg?color=blue)](LICENSE)

### Install

```shell
# install graalvm
brew install --cask graalvm/tap/graalvm-ce-java16

# bypass macos warning about unsigned software
sudo xattr -r -d com.apple.quarantine "/Library/Java/JavaVirtualMachines/graalvm-ce-java16-21.2.0"

# export env
export GRAALVM_HOME="/Library/Java/JavaVirtualMachines/graalvm-ce-java16-21.2.0/Contents/Home"

# install native image
$GRAALVM_HOME/bin/gu install native-image

# build single executable
./gradlew nativeCompile

# move out 
mv build/native/nativeCompile/html-to-epub-kotlin ./html-to-epub
```

### Usage
```shell
./html-to-epub *.html
```
Flags
```
 -author <arg>       set epub author
 -name <arg>         set epub name
 -cover <arg>        set epub cover
 -o,--output <arg>   output file
 -h,--help           print help
```
