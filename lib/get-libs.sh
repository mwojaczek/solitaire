#/usr/bin/env
rm -rf cards.zip
wget http://www.stud.fit.vutbr.cz/~xdokou12/IJA/cards.zip
rm -rf src/ija/ija2016/cards/
unzip cards.zip -d src/ija/ija2016/cards
