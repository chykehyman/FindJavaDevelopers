#!/bin/sh
#
# To skip running tests, run the --no-verify argument.
#       i.e   git commit --no-verify
#

## Run android 'check' task to check code quality.
./gradlew check --daemon

# Store the last exit code in a variable.
codeQualityResult=$?

# Perform output check
if [ $codeQualityResult -ne 0 ]
then
    echo "Code violations were found. Please fix them to proceed with commit"
    exit 1
fi

## Run project unit tests
./gradlew test --daemon

# Store the last exit code
testResult=$?

# Perform output check
if [ $testResult -ne 0 ]
then
    echo "Tests failed. Please fix them to proceed with commit"
    exit 1
fi

# Exit to commit, when all checks passes
exit 0