# Instaling Bot
Autor: Rozumek29 \
Version: 3.5

## Instalation Gudie
If you want download my bot, just go to the [releases](https://github.com/Rozumek29/Instaling-Bot/releases), download the newest version, lunch it, and enjoy :)

## How it works ?
The bot is logging in as you, it starts a daily session. It searches for a matching word in the database, if it is missing, a given word is omitted, I take the correct answer and add it to the database. Next time, the bot will already know this word and will give the correct answer.

## Manual
1. Enter your login and password in the appropriate tab and click ok.
2. Go to settings, you don't have to change anything if you don't want to. To change the browser to a different one, select "Other" and enter the path to the .exe file of your browser [The browser must be chrome-based (Opera, Brave e.c.t)].
3. Save settings, and start the bot.
### CHANGE LOG
    Version 3.5
        - Added Multithreading.
        - Database of learned words is now independent of updates.
    Version 3.0
        - Bot is now one .exe file, not big package like before.
        - Added GUI
        - Bot now support all browsers that are build on Chrome. (Chrome, Brave)
    Version 2.1
        - Added support for the Brave Browser.
    Version 2.0
        - Bot now throwing a new exception when login and password are not set in login.json
        - Now the browser that the bot is running has muted annoying sounds.
        - Now the ChromeDriver process gets killed right after the session ends.
        - Bot now saves his activities in the 'lastest.log' file in the 'log' folder
### MIT License.
[Check it out](https://github.com/Rozumek29/Instaling-Bot/blob/master/LICENSE)