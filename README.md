# MinecraftSnek
This project that I have coded is a snake game in minecraft. It uses the spigot server which allows me to code a plugin and add it into the plugins folder.

Minecraft Version: 1.17.1 (I try my best to update the code as soon as the spigot 1.18.1 or 1.18.2 plugin works).

Check out my youtube channel: [xPotato](https://www.youtube.com/channel/UCEhrd7gzYy-5OhEWL06T0zA)

## How to set up spigot server?
First you will need to download spigot-1.17.1.jar file to run the server [(link)](https://getbukkit.org/get/bf44510c50ddefccbaee1379c1f751de).

Then once you run the spigot-1.17.1.jar file, it will run just like a normal minecraft server. You will have to set the eula.txt file to true then run again to generate the world, nether-world, etc. It will also generate the plugin folder which is where you will need to add the MinecraftSnek plugin into.
## Download MinecraftSnek Plugin
You can straight download the plugin [here](https://drive.google.com/drive/folders/1y2Gb3EnFlTLyM-jg_UoSpQdN--HZNZgm?usp=sharing).

Alternatively, you may download/clone the code and generate the plugin which is a jar file.
You need to add the spigot-1.17.1.jar into your libraries folder.
To generate the jar file, it depends on your java IDE that you are running. In Apache Netbeans, you can just build the project and the jar file will appear in your project folder under dist.

## How to play the Snek Game?
Once you have have the server running with the plugin inside the plugins folder, when you enter your world it should have the same welcome message as shown in my youtube video (That's how you know the plugin is working). Make sure you op yourself by typing /op yourname in the server console to be able to use commands.

/board
- Enables you to create the board. Size of board defaults to 10 by 10.
- NOTE: height is along x-axis and width is along z-axis.

/board height width
- Replace height and width with the numbers you desire your board to be i.e. /board 3 4 creates a board with 3 height and 4 wide.

/play
- Will generate snek head and apple in the board.
- Add a piston timer then start playing using (iron ingot-LEFT, diamond ore-UP, gold ingot-DOWN, redstone-RIGHT).
- NOTE: UP is always towards postive of x-axis.

/reset
- Will clear the board and you can hit /play to play again.
