# MC Player Messages
A minecraft plugin that lets you customize player join, leave and death messages.

Minecraft version: 1.20

## Usage

A .jar file is available in releases. Just copy and paste the .jar and you are good to go.

It is recommended to create the `PlayerMessages` data folder yourself and a config file called `config.yml`, 
where you write your custom messages (the plugin will create it itself though but after the first launch of the plugin).  

## Config file

The syntax of the config file is following the yaml standards.

The custom join messages should be under the `join-messages:` label. There you will write a yaml list of strings.

```
join-messages:
    - 'message'
    - 'another message'
```

The leave and death messages correspond to `leave-messages:` and `death-messages:` labels respectively.

There is also a possibility for placeholders. Two placeholders ave available: `%player%` and `%entity%`. 

The `%player%` placeholder holds the corresponding player's name. It can be used in all kinds of messages.

The `%entity%` placeholder only can be used in death messages and holds the name of the entity that killed the player. Both placeholders also can be used in one string at the same time.

Chat color can also be altered with `'&'` and respective color code, for example, `&c` makes the text red.

```
join-messages:
    - 'The mighty %player% just &carrived!'
    
death-messages:
    - 'The mighty %player% was killed by %entity%'
```

An example config can be found in `src/.../resources` folder.

### Player-specific messages

There is also a possibility for specific players to have specific event messages. These players may
 be listed under the `players:` label. In lower level should be their name (not GUID) and next labels should be the message labels, following the syntax above.

```
players:
    player1:
        join-messages:
            - 'joined'
        
        leave-messages:
            - 'went'
```

Note that any of the labels can be omitted if you wish to not have custom messages of that certain type.

### Database

This plugin uses SQLite database. The database is used only for player-specific messages for optimal search. If a database error occurs the plugin will run without the db, but the player-specific messages will not be available.

The SQLite logic is implemented with the use of bridge pattern, so it should be easy (hopefully) to switch to other databases.