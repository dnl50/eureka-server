#!/usr/bin/env bash
set -euxo pipefail

# This script returns the value of a property from the gradle.properties file.
# Usage: ./get-gradle-property.sh "property-key-name"

# The file name to get the property's value from.
readonly PROPERTIES_FILE_NAME="gradle.properties"

echo `sed -rn "s/^$1=([^\n]+)$/\1/p" $PROPERTIES_FILE_NAME`
