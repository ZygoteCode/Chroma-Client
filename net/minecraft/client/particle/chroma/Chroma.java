package net.minecraft.client.particle.chroma;

import java.awt.Canvas;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.chroma.command.CommandManager;
import net.minecraft.client.particle.chroma.database.Database;
import net.minecraft.client.particle.chroma.database.DatabaseManager;
import net.minecraft.client.particle.chroma.database.databases.*;
import net.minecraft.client.particle.chroma.event.EventManager;
import net.minecraft.client.particle.chroma.friends.Friend;
import net.minecraft.client.particle.chroma.friends.FriendsManager;
import net.minecraft.client.particle.chroma.module.Category;
import net.minecraft.client.particle.chroma.module.Module;
import net.minecraft.client.particle.chroma.module.ModuleManager;
import net.minecraft.client.particle.chroma.module.gui.ClickGui;
import net.minecraft.client.particle.chroma.settings.Setting;
import net.minecraft.client.particle.chroma.settings.SettingsManager;
import net.minecraft.client.particle.chroma.ui.clickgui.ClickGUI;
import net.minecraft.client.particle.chroma.utils.LoginUtils;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Session;
import net.minecraft.client.particle.chroma.module.world.*;

public class Chroma
{
	private static String CLIENT_NAME = "Chroma";
	private static int CLIENT_BUILD = 7;
	private static String CLIENT_VERSION = "b" + Integer.toString(CLIENT_BUILD);
	private static String CLIENT_AUTHOR = "ZioJoJo";
	private static ModuleManager moduleManager;
	private static SettingsManager setManager;
	private static CommandManager cmdManager;
	private static DatabaseManager dbManager;
	private static ClickGUI clickGUI;
	
	public Chroma() throws IllegalArgumentException, IllegalAccessException, IOException, ClassNotFoundException
	{
		setManager = new SettingsManager();
		moduleManager = new ModuleManager();
		
		ArrayList<Module> mods = getModuleManager().sortModules(moduleManager.getModules(), false, false);
		
		getModuleManager().clearModules();
		
		for (Module m: mods)
		{
			getModuleManager().addModule(m);
		}
		
		cmdManager = new CommandManager();
		dbManager = new DatabaseManager();
		clickGUI = new ClickGUI();
		
		for (Database db: dbManager.getDatabases())
		{
			db.reload();
		}
		
		for (String s: dbManager.getDatabaseByName("toggled").getLines())
		{
			for (Module m: getModuleManager().getModules())
			{
				try
				{
					if (!(s == ""))
					{
						String[] factors = s.split("\\s+");
						int coso = Integer.parseInt(factors[0]);
						
						if (m.getId() == coso && m != null)
						{
							m.toggle();
						}
					}
				}
				catch (Exception e)
				{
					
				}
			}
		}
		
		for (String s: dbManager.getDatabaseByName("keys").getLines())
		{
			try
			{
				String[] factors = s.split("\\s+");
				int r1 = Integer.parseInt(factors[0]);
				int r2 = Integer.parseInt(factors[1]);
				
				getModuleManager().getModuleByID(r1).setKey(r2);
			}
			catch (Exception e)
			{
				
			}
		}
		
		for (String s: dbManager.getDatabaseByName("combos").getLines())
		{
			try
			{
				String[] factors = split(s, '|');
				int r1 = Integer.parseInt(factors[0]);
				String r2 = factors[1];
				
				getSetManager().getSettingById(r1).setValString(r2);
			}
			catch (Exception e)
			{
				
			}
		}
		
		for (String s: dbManager.getDatabaseByName("checks").getLines())
		{
			try
			{
				String[] factors = s.split("\\s+");
				int r1 = Integer.parseInt(factors[0]);
				boolean r2 = Boolean.parseBoolean(factors[1].toLowerCase());
				
				getSetManager().getSettingById(r1).setValBoolean(r2);
			}
			catch (Exception e)
			{
				
			}
		}
		
		for (String s: dbManager.getDatabaseByName("sliders").getLines())
		{
			try
			{
				String[] factors = s.split("\\s+");
				int r1 = Integer.parseInt(factors[0]);
				double r2 = Double.parseDouble(factors[1]);
				
				getSetManager().getSettingById(r1).setValueD(r2);
			}
			catch (Exception e)
			{
				
			}
		}
		
		for (String s: dbManager.getDatabaseByName("friends").getLines())
		{
			try
			{
				FriendsManager.addFriend(s, "");
			}
			catch (Exception e)
			{
				
			}
		}
		
		for (String s: dbManager.getDatabaseByName("configs").getLines())
		{
			try
			{
				FriendsManager.addFriend(s, "");
			}
			catch (Exception e)
			{
				
			}
		}
		
		endClient();
		
		//mcuddy66@gmail.com:blah@1966
		//saltsquid@gmail.com:Shoelace432
		//maesteryuna@gmail.com:tjem2005
		//tobylocke123@gmail.com:Alpine20
		//hikallon@yahoo.com:kallon105
		//vicki.hodgson89@hotmail.com:katyperry!
		/*CrazzyAlien@centrum.cz:Kocourek24
ElementalXtro@gmail.com:Boomer098
a.e.vidal@hotmail.es:p4nt4l30n
adwatt1207@gmail.com:dlftls0205
alexlarrarte@gmail.com:molinosalta7
arrantylerlangton@gmail.com:Langton24
aure.desmarez@hotmail.fr:aurelien59310
baptiste28000@gmail.com:Praline28
benb_is@msn.com:Windows64
btj30084@gmail.com:brent.j
costi.ciorobea@yahoo.com:thenightwish123
crazyrevolutioncereals@gmail.com:Dave3305
debets.leo@orange.fr:debets32110
dr.camaro71@yahoo.com:Tuckerman28
dragfeux16@gmail.com:CHEVROL2
edivaldo_egea@hotmail.com:calabousso
estebantirado3@gmail.com:cucukachu9876
etiennesetien@outlook.com:Thyaree7
ford_harley@hotmail.com:zewxss1234
german.fmp@hotmail.com:walfan13
ghb111x6@gmail.com:charter777
grouwel@gmail.com:chirby12
hotelboy91@gmail.com:Fla91chs
hunterbaumstark@gmail.com:sco3tr
jmca_95@hotmail.com:loscaceres4
jmubata@gmail.com:swishie12
jololho@gmail.com:lol4046
jon.desdavid@gmail.com:Silverghost1
jonr7870@gmail.com:Teddy920
julian-huck@web.de:latlorcorch1
justinsmall22@yahoo.com:Ravemaster23
kimundi@hotmail.com:kubacki101
lucasjnqr@gmail.com:dantas141
luisalvarez315@yahoo.com:GATWOOD1
marijn962@hotmail.com:nijram8
markans88@gmail.com:Pallad11
master_chief01@hotmail.com:SierraEcho75
matirix44@gmail.com:ZOHANlol1234
minecraftercriper@gmail.com:wasd3knopki
mir.winterberg@yahoo.de:THWJugend
nachoconesa1@gmail.com:161195161195m
needforcheese@yahoo.com:Vinceching1
neko_cath@hotmail.com:ntmuds123tp
nexon1@live.com:meowmeow255
nichlastz@hotmail.com:Hejmeddig34
nicholas1938@live.com:nickpoker121
nielstoet@gmail.com:aapgfqi7
niklas.pichler@gmx.at:ente1604
nikmahmutagic@gmail.com:Kikoman99
ordinyug@gmail.com:vvtfp32580
oskar.stalhandske@icloud.com:Wertpa00
peragrine123@gmail.com:Peragrinem5408
peterelectrical@hotmail.com:letmein123four
//
ppak5087@naver.com:rpfks0215
radman991@gmail.com:butbutmom
rodrex1998@gmail.com:rodrimax189
seanjackson1998@gmail.com:Taylor.123
serious-sam009@yandex.ru:4048082009SEM
shebarin220694@yandex.ru:PES220volt
//
skorpion798@gmx.de:10Euroschein
spacebulleter@gmail.com:Plakband12
spain_sebas_97@hotmail.com:Darkstar5002
spoxli@hotmail.com:EvaMarie1996
talavera652@hotmail.co.uk:detention666
taskinrits@gmail.com:2740rits
teddynsoki@gmail.com:spartan1003
theslymumbasa@gmail.com:Mumbasa676
//
theyeepi25@gmail.com:guigui25320
tom.stange1@web.de:FcB1900
toni.berns@web.de:dejalo20
tschkmc@yahoo.de:Tschiller1
vinzent-hawai-@hotmail.de:feneserok1
wiidzewa@mail.ru:pigifa67
zackeryschultz1999@gmail.com:zackery6969
zlynn91@gmail.com:300584ny
//
 * actor.jae.fashion@gmail.com:Jaestph1516!
piemuguen@Gmail.com:Noumea97!
noahdrover@gmail.com:Gsxr75001
janusbvoss@hotmail.dk:Janus123
adysonb12@gmail.com:Awesome2001!
Thom1855@gmail.com:Batman1855
min98yeh@gmail.com:Bigmac123890!
deer226@gmail.com:Tdfg4589!
chrisegan@mac.com:tommy2401
nicorange52@gmail.com:Zipzap126!
greekfreek23@gmail.com:Yodog123!
dery35@gmail.com:Tonys230!
spittaz565364@yahoo.com:Lucas2326!
cluk_rey@hotmail.com:Chris94523
calebgates76@gmail.com:Greenday76!
mediapop0@gmail.com:Sadrea9000!
nklimek02@gmail.com:Beastman1
kristo0800@gmail.com:Callofduty5!
knightsdc2k12@gmail.com:Trains23!
crazzyj62@gmail.com:Nicholas99!
janrenneberg@hotmail.de:Ingojan2010!
matthewbreice@gmail.com:Eminemisepic1
minecraftfan2503@gmail.com:Parmar88
jacobhagen6@gmail.com:Iamstar1ord
merfi.89@mail.ru:d89268155991
redwingrocks@gmail.com:Hungrychicken18
winstonsloan@hotmail.com:Bubmart06!
bhaisting@lightblast.net:Blackdog1!
carlos.rmoreira@hotmail.com:C4rl0s2102!
whybobwhy15@yahoo.com:kingcobra732
rayalan47@gmail.com:Alan2020!
shytzo@web.de:Cheppi4411!
johan.h.brevik@gmail.com:Lurven123
ms_starr@live.com:Sandking105!
layden.bg@hotmail.fr:Layden1234
natethenet@gmail.com:Lexmark1
charlienguyen688@yahoo.com:Ilovepancake123
kirbyofthestars@comcast.net:Allstars16
glennthing2@gmail.com:Twentyfour2!
edrick1998@gmail.com:Filipino12
michealchris711@gmail.com:Gizmomann1
allmightyalistar@gmail.com:Alistar4ever
bleik89@gmail.com:Ey375kudsa
jsbawsum@gmail.com:Stitchluver221!
littlemissmaia@hotmail.com:greencats222
rexsoro01@gmail.com:Daniel92!
wntermute@cox.net:Antares1
ceschinlori@gmail.com:skater666
xavierg208@gmail.com:Garcia01!
thisisbrooks@hotmail.com:Ducks1234
will.fava1@gmail.com:Claudinei1
jepulis12@gmail.com:Selekt12
fuzzypistol@gmail.com:Bannana1
aidenbrace@hotmail.co.uk:Chuchuchu7!
frediwalker95@gmail.com:Bruno1995
morgan168@live.com:ManUnited18
arthuraugusto.arde@gmail.com:carneand1
chaseconley40@gmail.com:Chasev40
rthomso@gmail.com:medbury2704
greenhatmatt@gmail.com:Sacred2011
mazzucajoseph@gmail.com:Mazzuca123!
robcanfly@gmail.com:Fr33f4ll
deah.crawley@yahoo.com:shayla12
ethanspeck1@gmail.com:Epicness967!
laurag463@yahoo.com:Gabriel12!
lanzenkiller@gmail.com:Ghost1012!
cm19901004@gmail.com:a23312512
powerball345@gmail.com:Fantastic1!
wylsimo@gmail.com:Enchant123
Bordattiffany@gmail.com:Sam2Dean!
epting.simon@yahoo.com:Thai1999
Aloreofironforge@hotmail.com:Astrofish1!
filda.spajda@gmail.com:Fanous258!
zakallen171@gmail.com:Bacon171!
shane.scifo@gmail.com:hockey1344
carter.schmidt03@gmail.com:Goobers03!
pxzlog@gmail.com:brenner123
jesspollock@hotmail.com:Jayson09
Joso5523@gmail.com:Yasamgay123
pharos93@gmail.com:oramu55
envy2343@gmail.com:Lollipop3!
joseph.darnaby@gmail.com:Bumpie26!
matic.bester123@gmail.com:Forfuny123!
eastunder123@yahoo.com:Westover112!
ipharr1@live.com:Scooby1993!
felixvarkus@gmail.com:Felixistgeil1
sebaoun.david@yahoo.fr:Diosoutia25!
collinvue@outlook.com:Liloazn12
victoria.wilson95@yahoo.com:Monster13!
mark.sueters@live.nl:ml07041995
bcraven10@live.com:Pepper1963!
sbousleiman@gmail.com:Purple123!
schaeferjonathan@t-online.de:Pohlmann4!
sarah.croly@btinternet.com:Jarvis113
peachadam19@yahoo.com:Letherblaka7!
kylechmn28@gmail.com:Pumpkin53!
wlshockman@gmail.com:1spelling
lucaz99@live.com.au:Defiant12
cherrychris79@yahoo.com:wallace12
williep1992@gmail.com:Peters14!
Xenoknightz@hotmail.co.uk:Jamie007!
pokdud312@gmail.com:Aaron2004
piotr.osipa@gmail.com:Ckak48jo
brboydan@bigpond.com:diamond1
gamer8727@gmail.com:Iamseven7!
jamez2043290@gmail.com:Eldikcurd2
sedool111@gmail.com:Pigcow123!
jehousoh@gmail.com:Asdzxc321!
franck.donin1964@orange.fr:Choupi14
ami0312@o2.pl:Amadeusz1!
a.j.b.99@hotmail.com:Legoland10!
maltepalte12@hotmail.com:Skalaskola1!
jonas.ogren@hotmail.com:Svampar123
brianfolkers@gmail.com:Triangle1!
rejectedwizard@gmail.com:Dixielee09
nddragoon3@gmail.com:Lukey1234
zero_phantomzx@yahoo.com:Tensazangetsu12
emirkaansaglik@hotmail.de:Emirkaan12
enzo.risdon17@gmail.com:Azertyuiop2!
Mwooldridge516@gmail.com:Xtd34ef3e!
hudsonaskins@gmail.com:Blank3216478
darkfangcopy@gmail.com:ninetails177
macielus100@gmail.com:maciek100
xxazzxx13@gmail.com:mancity13
kessist@gmail.com:Hundarna236411
smokinokie1976@aol.com:2007emma
Johntruong2047@hotmail.com:Itachi123!
siggisteini@live.com:Annel2419
david.boeggemann@gmx.net:Schnappi1!
robin_castrop@yahoo.de:Florida1!
ssj4gohan56@yahoo.com:Eternalfate123!
mart.haycock@gmail.com:Martanna1
reinier.v.linschoten@gmail.com:Superman1!
mihneafleseriu@hotmail.com:Mihnea2003
thom.hulsker@gmail.com:Kaaseter1!
alexwallander@gmail.com:Wally0204!
noahking@knology.net:Noahman11
neptunedagger86@gmail.com:Katieb0b!
tw21540@hotmail.com:Xxx24680
treehuggermilad@outlook.com:Something12
iAubree@hotmail.co.uk:RXH27tke!
luke.a.franceschini@gmail.com:Waycool11!
josko.sostar@gmail.com:Vokitoki4
frosi@turboprinz.de:schwarz111
skorm@ymail.com:Yahooz11!
alex.al-jenabi@web.de:Weristes123!
antonioc12@live.com:Antonioc1213!
somrod62@icloud.com:James911!
swader06@gmail.com:Pulsed1!
Edwarddecker37@gmail.com:Edmoney4115!
dylannelsontrott@gmail.com:Pickles9!
lmn13579@yahoo.com:Rncb1234
Maglottjacob@gmail.com:Deckstar94!
simid_g@hotmail.com:simidSIMIDsimid0
cormac@theburrden.com:Cl2sns123
boredomez@hotmail.com:Sieekay1!
bowseran@gmail.com:Collin246!
stuarthubbard11@gmail.com:Killthem123!
martino.pasqualotto.2002@gmail.com:Pasqua007
bagley.ilya@gmail.com:Rjdhbr3567
laurin-50@hotmail.de:Imperium123!
matthicks@live.ca:Hicm2311!
emilystephenson0141@gmail.com:Fashion01!
xfrogfishx@gmail.com:db147369
rgs1999@live.dk:Rasm9646!
emday86@gmail.com:Passwrd1
tmonsterman@gmail.com:Savage81!
screecher98@gmail.com:Uranium239!
gabrielplays153@gmail.com:Minhavidapc123
tcsoccer2000@gmail.com:Cooper123
wingzero00@live.com:Sandrock03
fatowl29@gmail.com:United50!
aidanmaloof@gmail.com:Candy0703
jaystatham2001@gmail.com:Deker4217
weto.2011@yahoo.com:death345
xthevoidhawkx@gmail.com:Viledune2!
tracy.penny1@googlemail.com:Smudge2432
elhombroske@gmail.com:Muffins99
roy.vink2@gmail.com:Royboy112
scrossman98@hotmail.com:Doofus29!
nicoleehop@live.com:Lilbabe123!
pilonithewhale@hotmail.com:Scottisme4ever123!
vegardnr2@hotmail.no:Bamsebasse123
tylernedkelly@hotmail.com:Tnk75500919
walo11@rocketmail.com:Magnitude24!
serge.leblanc25@gmail.com:Starcraft69!
brennaj@windowslive.com:Lworemmus112
maik.buessing.l.m@gmail.com:Mambono5
marcuslath75@gmail.com:Parsnip123!
cam9007@gmail.com:Slimebal1!
rubenacoelho2000@gmail.com:Ruben4321
alexander.j.burrell@gmail.com:Burrell1234
johannsjeffrey@gmail.com:Masjeff3!
nigelcampillos@gmail.com:Nigelsam1998!
woodaco@gmail.com:N25jboqt!
oscarwejher@yahoo.se:Oscar0505!
candykittenthe2nd@gmail.com:kitkat22
t.janisch@yahoo.com:Lolo1255!
jay100398@gmail.com:Jayrod0322!
dantebarbieri32@gmail.com:Leila2008
jamieholyoak@icloud.com:Connor20
sk.atom@hotmail.com:Sw0rdfish1!
mudi10@hotmail.de:mahmud123
maxwell.mandelik@hotmail.com:Gnar1984!
tomflitcroft@yahoo.com.au:bartrocks
silentslayere3@hotmail.com:Avashslayer1!
chililadwig@gmail.com:death2zombies
shai12444@gmail.com:SHI250500!
war-king@hotmail.de:Baumwolle34!
djwcardking@gmail.com:H7j8k9l0
bigbrojoe411@aol.com:katie1qaz
morten.halleland@hotmail.com:Simsons1!
generaljk77@aol.com:gogotank7
danielransom@live.com:Kaymen1234
thatfatalbino@gmail.com:Mmorpg97!
benollier@gmail.com:4Tanglewood3
dojodominator68@gmail.com:Pokemon68
wikifriki@hotmail.com:Mitataeslamejor1!
andresteioff@arcor.de:260788s31614
1magicshark1@gmail.com:Secret12!
antonscharf1992@googlemail.com:Mcrae2005
betwes1337@gmail.com:august32769dv
stephen.canh@gmail.com:FinkL5tien9
alexd57d@gmail.com:Blasi873!
skolmschate@hotmail.nl:Sebassie18
nikiforosgiapitzis@gmail.com:Magkas13579!
cmalemand@icloud.com:cmalemand10
blakerowlett@yahoo.com:Soccerpunc1!
hdro@arcor.de:P5qMcvrob
lukeftw01@gmail.com:Karatekid1!
oskagn@outlook.com:Agnes123
Awesomegamer229@gmail.com:David2003
abbrown1995@gmail.com:yellow27
caleb.martens@outlook.com:Moomoo2001
alexander.barron@hotmail.com:Fantasy12!
johnathan@themayosonline.com:Roblox6527!
karltonfields@gmail.com:Woodybuzz123!
shadowpersicom@gmail.com:Araleret1232
jolanvanimpe@hotmail.be:Wolfenstein123!
narusaku2008@gmail.com:6077095a
tempestanees@gmail.com:Pokahonta1
matteo.bertorotta@hotmail.com:Banaan2002!
nyahbolaura@gmail.com:Hondenpootjes01!
stevethetroper@gmail.com:Twt44445!
thomas@clark-family.eu:Tomccool1!
ben.clements@gmx.com:blink-182
Zewakker@gmail.com:Giants00
antebynz@hotmail.com:tavelram6541
kbpedersen@profibermail.dk:kristina99
torben-benner@t-online.de:Hallo1326
n.aleonis@tomac.ch:Iraklis1704
ceranco@gmail.com:nmwgnokh1309
cunliffe.josh@yahoo.com:summer
jamhz@aol.com:Hiromoto71
oschi69@gmx.de:anakin2009
tori.ferrari@hotmail.com:videoHI8
swillsdouglas@gmail.com:lucas0303
kenbenn22@gmail.com:kkbnntt6
djbasuroy@gmail.com:chotku007
geerrube00@gmail.com:geerkens1995
lfarnold@iastate.edu:Identity121
robico@mail.com:danger01
geers_jan@yahoo.de:Starwars22
tornet12@aol.com:maxie277
kendallsgraham@gmail.com:Spandex1
ernesto70@rocketmail.com:ernest1241293
hmana1959@gmail.com:CLrapax03
michaelwhitfield782@yahoo.com:Whitfield97
bkfurman11@yahoo.com:amdturion101
ces-ur@hotmail.com:Ces147963
ataterrito44@gmail.com:Bobobo98
Catseyepearl@yahoo.com:Benson1
jokkedewachter@hotmail.com:dtpride13
n.maire01@web.de:1qa2ws3edc
torres_juan45@yahoo.com:jt11261997
whiskeynwhisker@aol.com:Licky165!
simonheiss87@gmail.com:idefix2mal
jokzoo2@mail.com:bellab00
n.schnarkowski@web.de:493arceus
geisttog@yahoo.com:MDS1728!
curedant@hotmail.com:matenstraat116
erock25@comcast.net:Asdf1234
athanasioulucas@gmail.com:gtaagtaa1
jamierave1996@gmail.com:hack1996
sarcasta_b@yahoo.com:pookieroo1
Clynch769@gmail.com:Mouseclick528
n.tennet90@ntlworld.com:arishock2
osokind@yahoo.com:johndoe2
simonpierce1@hotmail.com:Monkeybutt619
jon.neely@icloud.com:P0W3RB00K
micheal_9_d@hotmail.com:Qawsed123
marcel@abts.info:2qt2bstr8!
robin.prette@gmail.com:Vulcain06
holden20067@hotmail.co.uk:solidus1
DC.Planb2@gmail.com:4three
qaz83430@gmail.com:zxcv7584150
alexholmgren82@gmail.com:SnuttePlutt1221
n_i_l_s@hotmail.com:elougart82
holdenvs97@gmail.com:Cooper05
simonsson_hannes@hotmail.com:Guldpeng1324
marcia.blakeman@hotmail.com:lgjackno7
DaBombza@gmail.com:Bagley11
erup123@hotmail.co.nz:htownkuz123
djnewman699@yahoo.com:spultd3210
alexian8791@yahoo.com:asdfjkl16
genar1015@hotmail.com:Qwe47471
cewkie@hotmail.co.uk:100961td
jan.bsoe@web.de:Jann123Bosse
djshiery@live.nl:veenendaal123
general.evans@gmail.com:Daedalus304!
marcialgarcia619@yahoo.com:jasmine1
sydneylockardmua@gmail.com:gunit4god
black_suzi@online.de:fromhell666
jonahsellers@att.net:thumper1
dju_despres@hotmail.com:Spaniard1
nacholamby@yahoo.com:hert49ne
simontungate@yahoo.com:Mollie171
marcin.kielce@g.pl:polopo333
alexis_xixon@hotmail.com:balonmano26
ottoromberg@hotmail.com:kjxy9812
lhobler@optus.net:005267
marco-thale@web.de:logisch1
szlek@op.pl:silownia200
blackhawk120mc@gmail.com:Galdian120
t.bormi@hotmail.de:Tobias1411
xxbloodycorps@gmail.com:Thomas97
applelove17@hotmail.fr:klmklm64
deerking88@gmail.com:Swordfish18
ricshome@yahoo.com:mikki1997
daan.strijbosch@westkreek.nl:8deadi2001
joey.edwards324@gmail.com:marina12
awwest@gmail.com:delancY0169
tk.hedman@telia.com:rymden11
coreyhayward2@gmail.com:Bailey211
wilmot7lb@btinternet.com:Dragon101
tia_wright@hotmail.com:princess2002
rhhktt@gmail.com:3Friends
ninchya@yahoo.com:qwerty22
tnkoeln@web.de:COOLaberLOL1
o.cazeault@hotmail.com:boudda6603732
annikahaag@web.de:biene137
luisfhilipe.rios@gmail.com:luis2003ll
valentinexander@gmail.com:neverknows
renegadejazz@hotmail.com:canon1012
jim.hoorn@gmail.com:jimbor2001
dragonman_75_04@yahoo.com:Tapco297
spydetectivejessica@yahoo.com:J0e3S2s3
sean.kim918@gmail.com:tjsdn1gh
xxxilyaxxx_89@mail.ru:Tihn199720
supereric7@hotmail.com:eric7748
cindy.pony@hotmail.co.uk:Cheekydog1
jok3r_35@yahoo.com:echelon1
batch27@hotmail.com:faith131
jfishback@fourway.net:squid2go
rankel@gmx.de:meingeil
cochranfamily3@gmail.com:cochran3
luvmyaidan@gmail.com:bridges10
napaemily1@icloud.com:Emy92045
alexandercandio04172001@gmail.com:raven11237
serrafimus@gmail.com:ueka6n5pdx
alessi-et-to@hotmail.it:irriducibile90
tr97@gmx.de:12361236as
kynbynt@gmail.com:kabnabpes3
wolterangelo@hotmail.nl:Yuppie11
davidspett@msn.com:kasaki123
ck.thomson@yahoo.com:Tenticles1
mr.kid10@hotmail.com:blgkrw57
aaronrossclarkson@live.co.uk:Cheddar14
davidjsandling@gmail.com:coolguy1370
primoz.trattnjek@gmail.com:hardi1235
pablolosandes@gmail.com:cero4795468
bromanium@gmail.com:vinter01
pwn100@live.se:palle123
holdenvs97@gmail.com:Cooper05
simonsson_hannes@hotmail.com:Guldpeng1324
marcia.blakeman@hotmail.com:lgjackno7
DaBombza@gmail.com:Bagley11
erup123@hotmail.co.nz:htownkuz123
djnewman699@yahoo.com:spultd3210
alexian8791@yahoo.com:asdfjkl16
genar1015@hotmail.com:Qwe47471
cewkie@hotmail.co.uk:100961td
jan.bsoe@web.de:Jann123Bosse
djshiery@live.nl:veenendaal123
general.evans@gmail.com:Daedalus304!
marcialgarcia619@yahoo.com:jasmine1
sydneylockardmua@gmail.com:gunit4god
black_suzi@online.de:fromhell666
jonahsellers@att.net:thumper1
dju_despres@hotmail.com:Spaniard1
nacholamby@yahoo.com:hert49ne
simontungate@yahoo.com:Mollie171
marcin.kielce@g.pl:polopo333
alexis_xixon@hotmail.com:balonmano26
ottoromberg@hotmail.com:kjxy9812
lhobler@optus.net:005267
marco-thale@web.de:logisch1
szlek@op.pl:silownia200
blackhawk120mc@gmail.com:Galdian120
t.bormi@hotmail.de:Tobias1411
xxbloodycorps@gmail.com:Thomas97
applelove17@hotmail.fr:klmklm64
deerking88@gmail.com:Swordfish18
ricshome@yahoo.com:mikki1997
daan.strijbosch@westkreek.nl:8deadi2001
joey.edwards324@gmail.com:marina12
awwest@gmail.com:delancY0169
tk.hedman@telia.com:rymden11
coreyhayward2@gmail.com:Bailey211
wilmot7lb@btinternet.com:Dragon101
tia_wright@hotmail.com:princess2002
rhhktt@gmail.com:3Friends
ninchya@yahoo.com:qwerty22
tnkoeln@web.de:COOLaberLOL1
o.cazeault@hotmail.com:boudda6603732
annikahaag@web.de:biene137
luisfhilipe.rios@gmail.com:luis2003ll
valentinexander@gmail.com:neverknows
renegadejazz@hotmail.com:canon1012
jim.hoorn@gmail.com:jimbor2001
Ameecrazygirl100@gmail.com:Ihatepurple911
marylinelorenc@gmail.com:fannytom62
mnplager@gmail.com:mnp50502
heldertc15@gmail.com:Helder1998
aditya.lakersfan@yahoo.com:lamborghini99
bastien.56@hotmail.fr:sadida56530
mrcorned@hotmail.com:coolest190
poyaa_12@hotmail.com:poyaerkul123
sngbs71@gmail.com:sonic112
Patrickmormile@comcast.net:patpat101
sarahdavis3199@hotmail.com:mermaid3199
aid.williams94@gmail.com:Iceblue94
pabrale@gmail.com:ahorasi77
chrisdagostino1234@gmail.com:gianna914
tweetygurl@optonline.net:pepper14
spease3@yahoo.com:St617ven
glaniewski09@gmail.com:hippies1
bobcatfootball26@gmail.com:bobcat26
truelove391@hotmail.com:rsz55391
kamshan67@gmail.com:Ivanlam124
schuster.parsdorf@web.de:Lennard2
paula.fionn@btinternet.com:fionntan123
klybhuka123@gmail.com:Fnot77899
stfuyeu@gmail.com:Saif2110
deane@venske.net:turtle25
stefanhoppentocht@gmail.com:Sammy1110
cjirvine88@gmail.com:itunes88
nicolasbelley2@gmail.com:Pepe2222
nesandvig@gmail.com:lustre22
rnenny@gmail.com:America29
filip9geib@gmail.com:gond64m32
jrobertsrhs@gmail.com:jonabug96
jheitmann@charter.net:Griff0909
blondie63@optusnet.com.au:snowy101
dedic72@web.de:ruwel1999
raltyinferno@gmail.com:gardavior
lexiebaugo@gmail.com:Alexandria448
nisse02@gmail.com:Negerper1992
rlara7123@gmail.com:Fapping1
sharpey397@iinet.net.au:Craptac13
mace1141@yahoo.com:050279bb
sebastian.fancygambler@gmail.com:Wanted88
josh_schuelke@yahoo.com:Maverick7
cynagen@gmail.com:Shitfac3
jordanpaq@gmail.com:itsallgood1
corduba82@yahoo.com:Ninjago4316911
lildoron@gmail.com:kidswb123ty
james.smart3@gmail.com:daffyduck1
lucymcnally@hotmail.co.uk:lmcnally651
matt.bosanquet@ntlworld.com:alfie2007
rasmusstrong@gmail.com:82Macbuckley333
cai_roehme@hotmail.com:Brattvoll66
nmacaroni911@gmail.com:rachel16
maxneetens@gmail.com:Rijstpap5
damon.rayner@gmail.com:donkey23
brandoncosta638@gmail.com:heatran141
thoops23@hotmail.com:jordan23
tobie.phillips6@gmail.com:Wildcats8
redamon123@yahoo.com:Trebor123
hallchristopher22@yahoo.co.uk:Jbcrisy22
alejandrorico17@yahoo.com:alexrico2429
green.matthew1@gmail.com:Kopeee56
cherrybear0425@yahoo.com:Iamfree425
fourthelement@hotmail.com:Lotus501
jenniecoles2403@hotmail.co.uk:GetFucked2711
pascalsergent@hotmail.fr:pas190176
j-p.leveille@live.ca:Trertre07

someonesparkles2@gmail.com:Boobootoo22
SlamCam0324@gmail.com:Boster0324
jcichella@gmail.com:jack101303
carterstere@gmail.com:5salsa
braydenmealer@gmail.com:mealer99
lannies3kids@gmail.com:astroboy2007
arwenhelms@gmail.com:1apah0ch
mattrulz617@gmail.com:Ma010243

*/
		
		//LoginUtils.authenticate("vicki.hodgson89@hotmail.com", "katyperry!");
		//LoginUtils.authenticate("saltsquid@gmail.com", "Shoelace432");
		
		// MAIN ACCOUNT
		
		//LoginUtils.authenticate("gabryprog@gmail.com", "minkiayoyoteloficco66A#");
		//System.out.println("TOKEN: " + Minecraft.getMinecraft().getSession().token);
	    Minecraft.getMinecraft().session = new Session("ZioEren", "997feba7-1f24-4cbb-8423-51979ed8fc5c", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIwN2ViNTdlNjA0NDJhODJkZGMxMGQwMDZjZjE1ZjE0ZSIsInlnZ3QiOiI0MDM1MjVlZjU0ZDY0YzIyYjM4ZmQ2OGI4ZjRiZWFlYSIsInNwciI6Ijk5N2ZlYmE3MWYyNDRjYmI4NDIzNTE5NzllZDhmYzVjIiwiaXNzIjoiWWdnZHJhc2lsLUF1dGgiLCJleHAiOjE2MDgxNjA1MzMsImlhdCI6MTYwNzk4NzczM30.nI_weXl8JsMzn-1ds7t1Zf7GBrJeUVkd04RL13OREoo", "MOJANG");
		
		// HYPIXEL ALT
		//LoginUtils.authenticate("seanjackson1998@gmail.com", "Taylor.123");
		//System.out.println("TOKEN: " + Minecraft.getMinecraft().getSession().token);
	    //Minecraft.getMinecraft().session = new Session("Emirates1998", "d85e2456-b82f-4877-8d03-94381f9c6284", ".eyJzdWIiOiJkZDRjNWYxOGQ0MjE0ODExOGRkMThmYWQyYmE1NzNmZCIsIm5iZiI6MTU3ODI0ODQwMiwieWdndCI6IjcwMThlZmVkN2VhNzRiY2VhOWIwZTI5NjllZDIwYmQ1Iiwic3ByIjoiZDg1ZTI0NTZiODJmNDg3NzhkMDM5NDM4MWY5YzYyODQiLCJyb2xlcyI6W10sImlzcyI6ImludGVybmFsLWF1dGhlbnRpY2F0aW9uIiwiZXhwIjoxNTc4NDIxMjAyLCJpYXQiOjE1NzgyNDg0MDJ9.oAOGPjx2EBnfUnd-2j80nGBpzIdJeWrdJx5MyqrNxWY", "MOJANG");
	    
		// HYPIXEL ALT
		//LoginUtils.authenticate("war-king@hotmail.de", "Baumwolle34!");
		
	    initDiscordRPC();
	}
	
    public void initDiscordRPC()
    {
    	DiscordRPC.discordShutdown();
    	
        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler((user) ->
        {
            DiscordRichPresence.Builder presence = new DiscordRichPresence.Builder("Score: ");
            presence.setDetails("Running Test");
            DiscordRPC.discordUpdatePresence(presence.build());
        }).build();
        
        DiscordRPC.discordInitialize("415885161457123338", handlers, false);
        DiscordRPC.discordRegister("415885161457123338", "");
        DiscordRPC.discordRunCallbacks();
    }
	
	public static String[] split(String string, char separator)
	{
	    int count = 1;
	    for (int index = 0; index < string.length(); index++)
	        if (string.charAt(index) == separator)
	            count++;
	    String parts[] = new String[count];
	    int partIndex = 0;
	    int startIndex = 0;
	    for (int index = 0; index < string.length(); index++)
	        if (string.charAt(index) == separator)
	        {
	            parts[partIndex++] = string.substring(startIndex, index);
	            startIndex = index + 1;
	        }
	    
	    parts[partIndex++] = string.substring(startIndex);
	    return parts;
	}
	
	public void deleteConfig(String name)
	{
		name = name.toLowerCase();	
		dbManager.getDatabaseByName(name + "Combos").delete();
		dbManager.getDatabaseByName(name + "Checks").delete();
		dbManager.getDatabaseByName(name + "Sliders").delete();
		dbManager.getDatabaseByName(name + "Toggled").delete();
	}
	
	public void createConfig(String name)
	{
		dbManager.addDatabase(new Database(name + "Toggled"));
		dbManager.addDatabase(new Database(name + "Checks"));
		dbManager.addDatabase(new Database(name + "Sliders"));
		dbManager.addDatabase(new Database(name + "Combos"));
		
		for (Database db: dbManager.getDatabases())
		{
			if (db.getName() == name + "toggled")
			{
				db.setData("");
				
				for (Module m: getModuleManager().getModules())
				{
					if (m.isToggled())
					{
						db.addLine(Integer.toString(m.getId()));
					}
				}
			}
			else if (db.getName() == name + "combos")
			{
				db.setData("");
				
				for (Setting set: getSetManager().getSettings())
				{
					if (set.isCombo())
					{
						db.addLine(Integer.toString(set.getId()) + "|" + set.getValString());
					}
				}
			}
			else if (db.getName() == name + "sliders")
			{
				db.setData("");
				
				for (Setting set: getSetManager().getSettings())
				{
					if (set.isSlider())
					{
						db.addLine(Integer.toString(set.getId()) + " " + Double.toString(set.getValueD()));
					}
				}
			}
			else if (db.getName() == name + "checks")
			{
				db.setData("");
				
				for (Setting set: getSetManager().getSettings())
				{
					if (set.isCheck())
					{
						db.addLine(Integer.toString(set.getId()) + " " + Boolean.toString(set.getValBoolean()));
					}
				}
			}
			
			db.save();
			db.reload();
		}
	}
	
	public void loadConfig(String name)
	{
		name = name.toLowerCase();
		
		for (Module m: getModuleManager().getModules())
		{
			if (m.isToggled())
			{
				m.toggle();
			}
		}
		
		for (String s: dbManager.getDatabaseByName(name + "combos").getLines())
		{
			try
			{
				String[] factors = split(s, '|');
				int r1 = Integer.parseInt(factors[0]);
				String r2 = factors[1];
				
				getSetManager().getSettingById(r1).setValString(r2);
			}
			catch (Exception e)
			{
				
			}
		}
		
		for (String s: dbManager.getDatabaseByName(name + "checks").getLines())
		{
			try
			{
				String[] factors = s.split("\\s+");
				int r1 = Integer.parseInt(factors[0]);
				boolean r2 = Boolean.parseBoolean(factors[1].toLowerCase());
				
				getSetManager().getSettingById(r1).setValBoolean(r2);
			}
			catch (Exception e)
			{
				
			}
		}
		
		for (String s: dbManager.getDatabaseByName(name + "sliders").getLines())
		{
			try
			{
				String[] factors = s.split("\\s+");
				int r1 = Integer.parseInt(factors[0]);
				double r2 = Double.parseDouble(factors[1]);
				
				getSetManager().getSettingById(r1).setValueD(r2);
			}
			catch (Exception e)
			{
				
			}
		}
		
		for (String s: dbManager.getDatabaseByName(name + "toggled").getLines())
		{
			for (Module m: getModuleManager().getModules())
			{
				try
				{
					if (!(s == ""))
					{
						String[] factors = s.split("\\s+");
						int coso = Integer.parseInt(factors[0]);
						
						if (m.getId() == coso && m != null)
						{
							m.toggle();
						}
					}
				}
				catch (Exception e)
				{
					
				}
			}
		}
	}
	
	public void endClient() throws IllegalArgumentException, IllegalAccessException, IOException
	{
		for (Database db: dbManager.getDatabases())
		{
			if (db.getName() == "toggled")
			{
				db.setData("");
				
				for (Module m: getModuleManager().getModules())
				{
					if (m.isToggled())
					{
						db.addLine(Integer.toString(m.getId()));
					}
				}
			}
			else if (db.getName() == "keys")
			{
				db.setData("");
				
				for (Module m: getModuleManager().getModules())
				{
					db.addLine(Integer.toString(m.getId()) + " " + Integer.toString(m.getKey()));
				}
			}
			else if (db.getName() == "combos")
			{
				db.setData("");
				
				for (Setting set: getSetManager().getSettings())
				{
					if (set.isCombo())
					{
						db.addLine(Integer.toString(set.getId()) + "|" + set.getValString());
					}
				}
			}
			else if (db.getName() == "sliders")
			{
				db.setData("");
				
				for (Setting set: getSetManager().getSettings())
				{
					if (set.isSlider())
					{
						db.addLine(Integer.toString(set.getId()) + " " + Double.toString(set.getValueD()));
					}
				}
			}
			else if (db.getName() == "checks")
			{
				db.setData("");
				
				for (Setting set: getSetManager().getSettings())
				{
					if (set.isCheck())
					{
						db.addLine(Integer.toString(set.getId()) + " " + Boolean.toString(set.getValBoolean()));
					}
				}
			}
			else if (db.getName() == "friends")
			{
				db.setData("");
				
				for (Friend friend: FriendsManager.friends)
				{
					if (FriendsManager.isFriend(friend.getName()))
					{
						db.addLine(friend.getName());
					}
				}
			}
			
			db.save();
			db.reload();
		}
	}
	
	public static ClickGUI getClickGUI()
	{
		return clickGUI;
	}
	
	public static void message(String msg)
	{
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§9Chroma> §7" + msg.replace("&", "§")));
	}
	
	public static boolean onCommand(String s)
	{
		if (s.startsWith(getCmdTrigger()) && !Minecraft.getMinecraft().getChroma().getModuleManager().getModuleByID(0).isToggled())
		{
			cmdManager.callCommand(s.substring(getCmdTrigger().length()));
			return false;
		}
		
		return true;
	}
	
	public static String getCmdTrigger()
	{
		return "?";
	}
	
	public static boolean isGhostMode()
	{
		return moduleManager.getModuleByID(0).isToggled();
	}
	
	public static ModuleManager getModuleManager()
	{
		return moduleManager;
	}
	
	public static SettingsManager getSetManager()
	{
		return setManager;
	}
	
	public static CommandManager getCmdManager()
	{
		return cmdManager;
	}
	
	public static DatabaseManager getDbManager()
	{
		return dbManager;
	}
	
	public static int getFirstModuleLength()
	{
		return getModuleManager().getModules().get(0).getName().length();
	}
	
	public static int getFirstCategoryLength()
	{
		ArrayList<Category> sortedCategories = new ArrayList<Category>();
		
		int length = 20;
		
		for (int j = 0; j < 20; j++)
		{
			length--;
			
			for (Category cat: Category.values())
			{
				String name = cat.name().substring(0, 1) + cat.name().substring(1).toLowerCase();
				
				if (name.length() == length)
				{
					sortedCategories.add(cat);
				}
			}
		}
		
		return sortedCategories.get(0).name().length();
	}
	
	public static String getClientName()
	{
		return CLIENT_NAME;
	}
	
	public static void setClientName(String n)
	{
		CLIENT_NAME = n;
	}
	
	public static String getClientVersion()
	{
		return CLIENT_VERSION;
	}
	
	public static void setClientVersion(String v)
	{
		CLIENT_VERSION = v;
	}
	
	public static String getClientAuthor()
	{
		return CLIENT_AUTHOR;
	}
	
	public static void setClientAuthor(String a)
	{
		CLIENT_AUTHOR = a;
	}
	
	public static int getClientBuild()
	{
		return CLIENT_BUILD;
	}
	
	public static void setClientBuild(int b)
	{
		CLIENT_BUILD = b;
	}
}