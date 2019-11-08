
package shell;

import java.io.File;
import java.util.Scanner;

public class Rush extends Shell{
    
    Character[][] table;
    int sor;
    int oszlop;
    int kisor;
    int kioszlop;
    int s;
    int o;
    boolean vege;
    boolean load;
    
    
    public boolean check(Character c)
    {
        boolean exists=false;
        for(int i=0; i<sor; i++)
        {
            for(int j=0; j<oszlop; j++)
            {
                if(table[i][j]==c)
                {
                    exists=true;
                    break;
                }
            }
        }
        return exists;
    }
    public void index(Character c)
    {
        boolean exists=false;
        for(int i=0; i<sor; i++)
        {
            if(exists) break;
            for(int j=0; j<oszlop; j++)
            {
                if(table[i][j]==c)
                {
                    s=i;
                    o=j;
                    exists=true;
                    break;
                }
            }
        }
    }
    public boolean felette(Character c, int s, int o)
    {
        if(s==0) return false;
        if(o!=0)
        {
            if(table[s][o-1] == c) return false;
        }
        if(!(o==oszlop-1))
        {
            if(table[s][o+1] == c) return false;
        }
        if(!(table[s-1][o] == '.')) return false;
        
        return true;
    }
    public boolean alatta(Character c, int s, int o)
    {
        if(s==sor-1) return false;
        if(o!=0)
        {
            if(table[s][o-1] == c) return false;
        }
        if(!(o==oszlop-1))
        {
            if(table[s][o+1] == c) return false;
        }
        int aktsor=s;
        while(aktsor<sor-1)
        {
            if(table[aktsor][o] == c)
            {
                aktsor++;
            }
            else break;
        }
        if(table[aktsor][o]!='.')
        {
            return false;
        }
            
        
        return true;
    }
    public boolean jobbramellette(Character c, int s, int o)
    {
        if(o==oszlop-1) return false;
        if(s==0)
        {
            if(table[s+1][o] == c) return false;
        }
        else if(s==sor-1)
        {
            if(table[s-1][o] == c) return false;
        }
        else if(table[s+1][o] == c || table[s-1][o] == c)
        {
            return false;
        }
        int aktoszlop=o;
        while(aktoszlop<oszlop-1)
        {
            if(table[s][aktoszlop] == c)
            {
                aktoszlop++;
            }
            else break;
        }
        //format("AKTOSZLOP: %d\n", aktoszlop);
        if(table[s][aktoszlop]!='.')
        {
            return false;
        }
            
        
        return true;
    }
    public boolean balramellette(Character c, int s, int o)
    {
        if(o==0) return false;
        if(s==0)
        {
            if(table[s+1][o] == c) return false;
        }
        else if(s==sor-1)
        {
            if(table[s-1][o] == c) return false;
        }
        else if(table[s+1][o] == c || table[s-1][o] == c)
        {
            return false;
        }
        int aktoszlop=o;
        while(aktoszlop>0)
        {
            if(table[s][aktoszlop] == c)
            {
                aktoszlop--;
            }
            else break;
        }
        //format("AKTOSZLOP: %d\n", aktoszlop);
        if(table[s][aktoszlop]!='.')
        {
            return false;
        }
            
        
        return true;
    }
    
    
    public void prt()
    {
        for(int i=0; i<sor; i++)
        {
            for(int j=0; j<oszlop; j++)
            {
                format("%c ", table[i][j]);
            }
            format("\n");
            
            
        }
        if(table[kisor-1][kioszlop-1] == 'P') vege=true;
        if(vege)
        {
            format("Nyertél!\n");
        }
    }
    
    public Rush()
    {
        
        addCommand(new Command("load") {
            @Override
            public boolean execute(String... args) {
                if(args.length!=1)
                {
                    format("Egy paraméter kötelező!\n");
                    return false;
                }
                try
                {
                    File file=new File(args[0]);
                    Scanner sc = new Scanner(file);
                    sor=sc.nextInt();
                    oszlop=sc.nextInt();
                    kisor=sc.nextInt();
                    kioszlop=sc.nextInt();
                    table = new Character[sor][oszlop];
                    sc.nextLine();
                    int aktsor=0;
                    while(sc.hasNextLine())
                    {
                        String junk=sc.nextLine();
                        int aktoszlop=0;
                        for(int j=0; j<oszlop; j++)
                        {
                            table[aktsor][aktoszlop++]=junk.charAt(j);
                        }
                        aktsor++;
                        //format("\n");
                    } 
                }
                catch(Exception e)
                {
                    format("Nem tölthető be!\n");
                    return false;
                }
                prt();
                load=true;
                return true;
            }
        });
        
        addCommand(new Command("print") {
            @Override
            public boolean execute(String... args) {
                if(args.length!=0)
                {
                    format("Nem lehet paramétere!\n");
                    return false;
                }
                if(!load)
                {
                    format("Nincs még betöltve!\n");
                    return false;
                }
                prt();
                return true;
            }
        });
        addCommand(new Command("north") {
            @Override
            public boolean execute(String... args) {
                if(args.length!=1)
                {
                    format("1 paramétere kell hogy legyen!\n");
                    return false;
                }
                if(!load)
                {
                    format("Nincs még betöltve!\n");
                    return false;
                }
                if(vege)
                {
                    format("Vége a játéknak!\n");
                    return false;
                }
                if(args[0].length()!=1)
                {
                    format("Karaktert kell megadni!\n");
                    return false;
                }
                try
                {
                    Character c = args[0].charAt(0);
                    if(!Character.isAlphabetic(c))
                    {
                        format("Nem betű!\n");
                        return false;
                    }
                    else
                    {
                        if (!check(c))
                        {
                            format("Nincs ilyen autó!\n");
                            return false;
                        }
                        
                        index(c);
                        //format("Index : %d %d\n", s, o);
                        if(felette(c,s,o))
                        {
                            table[s-1][o] = c;
                            int akts=s;
                            while(akts<=sor-1)
                            {
                                if(table[akts][o]==c) akts++;
                                else break;
                            }
                            table[akts-1][o]='.';
                        }
                        else
                        {
                            format("Nem tolható feljebb!\n");
                            return false;
                        }
                        
                        
                    }
                }
                catch(Exception e)
                {
                    return false;
                }
                prt();
                return true;
            }
        });
        addCommand(new Command("south") {
            @Override
            public boolean execute(String... args) {
                if(args.length!=1)
                {
                    format("1 paramétere kell hogy legyen!\n");
                    return false;
                }
                if(!load)
                {
                    format("Nincs még betöltve!\n");
                    return false;
                }
                if(vege)
                {
                    format("Vége a játéknak!\n");
                    return false;
                }
                if(args[0].length()!=1)
                {
                    format("Karaktert kell megadni!\n");
                    return false;
                }
                try
                {
                    Character c = args[0].charAt(0);
                    if(!Character.isAlphabetic(c))
                    {
                        format("Nem betű!\n");
                        return false;
                    }
                    else
                    {
                        if (!check(c))
                        {
                            format("Nincs ilyen autó!\n");
                            return false;
                        }
                        
                        index(c);
                        //format("Index : %d %d\n", s, o);
                        if(alatta(c,s,o))
                        {
                            //format("Siker\n");
                            table[s][o]='.';
                            int aktsor = s+1;
                            while (aktsor <= sor-1)
                            {
                                if (table[aktsor][o] == '.') {
                                    break;
                                }
                                else
                                {
                                    aktsor++;
                                }
                            }
                            //format("%d\n", aktsor);
                            table[aktsor][o]=c;
                        }
                        else
                        {
                            format("Nem tolható lentebb!\n");
                            return false;
                        }
                        
                        
                    }
                }
                catch(Exception e)
                {
                    return false;
                }
                prt();
                return true;
            }
        });
        addCommand(new Command("east") {
            @Override
            public boolean execute(String... args) {
                if(args.length!=1)
                {
                    format("1 paramétere kell hogy legyen!\n");
                    return false;
                }
                if(!load)
                {
                    format("Nincs még betöltve!\n");
                    return false;
                }
                if(vege)
                {
                    format("Vége a játéknak!\n");
                    return false;
                }
                if(args[0].length()!=1)
                {
                    format("Karaktert kell megadni!\n");
                    return false;
                }
                try
                {
                    Character c = args[0].charAt(0);
                    if(!Character.isAlphabetic(c))
                    {
                        format("Nem betű!\n");
                        return false;
                    }
                    else
                    {
                        if (!check(c))
                        {
                            format("Nincs ilyen autó!\n");
                            return false;
                        }
                        
                        index(c);
                        //format("Index : %d %d\n", s, o);
                        if(jobbramellette(c,s,o))
                        {
                            table[s][o]='.';
                            int aktoszlop = o+1;
                            while (aktoszlop < oszlop-1)
                            {
                                if (table[s][aktoszlop] == '.') {
                                    break;
                                }
                                else
                                {
                                    aktoszlop++;
                                    //format("%d %d\n", s, aktoszlop);
                                }
                            }
                            //format("%d\n", aktsor);
                            table[s][aktoszlop]=c;
                        }
                        else
                        {
                            format("Nem tolható jobbra!\n");
                            return false;
                        }
                        
                        
                    }
                }
                catch(Exception e)
                {
                    return false;
                }
                prt();
                return true;
            }
        });
        addCommand(new Command("west") {
            @Override
            public boolean execute(String... args) {
                if(args.length!=1)
                {
                    format("1 paramétere kell hogy legyen!\n");
                    return false;
                }
                if(!load)
                {
                    format("Nincs még betöltve!\n");
                    return false;
                }
                if(vege)
                {
                    format("Vége a játéknak!\n");
                    return false;
                }
                if(args[0].length()!=1)
                {
                    format("Karaktert kell megadni!\n");
                    return false;
                }
                try
                {
                    Character c = args[0].charAt(0);
                    if(!Character.isAlphabetic(c))
                    {
                        format("Nem betű!\n");
                        return false;
                    }
                    else
                    {
                        if (!check(c))
                        {
                            format("Nincs ilyen autó!\n");
                            return false;
                        }
                        
                        index(c);
                        //format("Index : %d %d\n", s, o);
                        if(balramellette(c,s,o))
                        {
                            table[s][o-1]=c;
                            int aktoszlop = o+1;
                            while (aktoszlop <= oszlop-1)
                            {
                                if (table[s][aktoszlop] == c)
                                {
                                    if(aktoszlop==oszlop-1) break;
                                    aktoszlop++;
                                }
                                else
                                {
                                    aktoszlop--;
                                    break;
                                    //format("%d %d\n", s, aktoszlop);
                                }
                            }
                            //format("%d\n", aktsor);
                            table[s][aktoszlop]='.';
                        }
                        else
                        {
                            format("Nem tolható balra!\n");
                            return false;
                        }
                        
                        
                    }
                }
                catch(Exception e)
                {
                    return false;
                }
                prt();
                return true;
            }
        });
    }
    
}
