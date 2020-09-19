#!/usr/bin/env bash

# Sets the value of the property containing the current version of the project to the value of the
# property containing the next version and increments the next version's patch version by 1.

# The file name to set the property's values in.
readonly PROPERTIES_FILE_NAME="gradle.properties"

# The key of the property containing the current version of the project.
readonly CURRENT_VERSION_PROP_KEY="currentProjVersion"

# The key of the property containing the next version of the project.
readonly NEXT_VERSION_PROP_KEY="nextProjVersion"

# Get the value of property holding the next version
nextProjVersion=`./get-gradle-property.sh "$NEXT_VERSION_PROP_KEY"`

# Replace the current with the next version
sed -r -i "s/^$CURRENT_VERSION_PROP_KEY=.*/$CURRENT_VERSION_PROP_KEY=$nextProjVersion/" $PROPERTIES_FILE_NAME

# Split into minor, major and patch and increment the patch version
# Remove trailing whitespace from the patch version (just in case)
majorVersion=`echo $nextProjVersion | cut -d'.' -f1`
minorVersion=`echo $nextProjVersion | cut -d'.' -f2`
patchVersion=`echo $nextProjVersion | cut -d'.' -f3 | sed 's/ *$//g'`
incrementedPatchVersion=$((++patchVersion))

# put it all back together
incrementedNextProjVersion="${majorVersion}.${minorVersion}.${incrementedPatchVersion}"

# Give some feedback
echo "Next version will be: $incrementedNextProjVersion"

# Replace the current next version with the incremented next version
sed -r -i "s/^$NEXT_VERSION_PROP_KEY=.*/$NEXT_VERSION_PROP_KEY=$incrementedNextProjVersion/" $PROPERTIES_FILE_NAME
