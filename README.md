# Superdamager
A mc-spigot damager plugin. The .jar is in the server/plugins folder

## How to use  
1. Start plugin once -> All neccessary .yml files will be created  
2. Add difficulties to the difficulties.yml in the following pattern:  
difficulty_1:  
&nbsp;&nbsp;# This is the difficulty name, it's important for later  
&nbsp;&nbsp;name: easy  
&nbsp;&nbsp;# This is the damage that is dealt every 20/tick_rate seconds  
&nbsp;&nbsp;damage: 5  
&nbsp;&nbsp;# The tick_rate to determinte how often the player will be damaged  
&nbsp;&nbsp;tick_rate: 20  
3. Create damagers with the following command:
/sd create _difficulty_ _radius_ 
> This creates a square damager with the given difficulty and size
> difficulty should be replaced with the difficulty name specified in the difficulties.yml
> radius should be replaced with an integer to determine the size of the damager  
4. Get a list of all your damagers with /sd damagers  
5. Get a list of all your difficulties with /sd difficulties  
6. Remove a damager with /sd delete when standing nearby the damager  
> Removes the nearest damager  
