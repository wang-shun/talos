VERSION=2.0.1-SNAPSHOT
#----------------------------------------------------

mvn versions:set -DnewVersion=${VERSION}
find ../ -name pom.xml.versionsBackup | xargs rm -rf

echo "\n"
echo "【注意】如无特殊版本需要，请将当前目录pom.xml下的properties[bpm-talos.version]手动改为${VERSION} ! \n"
