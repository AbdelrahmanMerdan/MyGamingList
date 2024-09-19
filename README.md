# MyGamingList

An application that allows users to view PC games, basic game statistics, rate/review, and engage with the community.

# How to Build and Compile:
Make sure you have at least Java ver.17 and above installed, and that your IDE has any type of Gradle integration installed. 

Download and import source code as a Gradle project.

Refresh project by right-clicking on MyGamingList, then going to Gradle -> Refresh Gradle Project.

Wait for several seconds for the necessary libararies to be installed.

To configure Gradle project and its tasks, please look at build.gradle and settings.gradle file.

By default, running the assemble task will build and launch the program. While running the build task will run all tests first, before building and launching the program.

### Note:
* It takes around 12 seconds to launch the program.
* May not work anymore due to timeout error: MongoDB times out the cluster if it has been inactive for 60 days. Since the cluster is not being actively connected to, the cluster may time out and the program won't be able to launch. While it can be re-activated, some features such as the autocomplete search will not work due to the reset.

### Cluster Status: 
Re-activated on September 19, 2024

# Supported Languages for User Reviews and Comments:
English, Chinese, Japanese, Vietnamese, Swedish, and any other languages that uses the English, Latin, Arabic, Cyrillic, or Greek alphabet.

# Resources:

## API:
Steam WEB API
## Database Platform:
MongoDB

# Documentation:

## API:
i) https://steamapi.xpaw.me/<br>
ii) https://github.com/Revadike/InternalSteamWebAPI

## MongoDB:
i) https://www.mongodb.com/docs/
