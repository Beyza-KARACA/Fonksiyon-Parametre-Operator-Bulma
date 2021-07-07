/**  
 *  
 * @author Beyza KARACA beyza.karaca@ogr.sakarya.edu.tr / Çiğdem Sıla UĞURLU sila.ugurlu@ogr.sakarya.edu.tr  
 * @since 1 Mart 2019
 * <p>  
 * Ödevde sorgulamalarımızı regex ifadeler kullanarak yaptık.
 * Daha sonra regex ile uyan ifadeleri yani match.groupları alıp fonksiyon ve parametre isimlerini bulduk.
 * Regexlerde operatörlerin uymasına göre operatör sayısını 1 arttırdık ve operatör sayımızı bulduk.
 * </p>  
 */ 

package b161210367.b161210052;

import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class B161210367B161210052 {

   //Bu bölgede pattern matcher ve regex ifadelerimizi yazdık.
    
    private static Pattern fonksiyon;
    private static Matcher fonksiyonMatches;
    
    private static final String FonksiyonRegex ="((\\s)+)?(const(\\s)+)?(int|double|float|void|char)(\\s)+[a-zA-Z]+\\((((int|double|float|void|char)(\\s)+(&|\\*)*?[a-zA-Z]*|(,(int|double|float|void|char)(\\s)+(&|\\*)*?[a-zA-Z]*))*)?\\)(\\s)*[{]*?(\\s)*?[\\\\n]*";
   
    private static Pattern fonksiyonadi;
    private static Matcher fonksiyonadiMatches;
    
    private static final String FonksiyonAdiRegex ="(\\s)+[a-zA-Z]*(\\s)*?[(]";
   
    private static Pattern parametre ;
    private static Matcher parametreMatcher ;
    
    private static final String ParametreSayisiRegexi =	"(\\s)+[&|\\*]?[a-zA-Z]*[^(]([,]|[)])(\\s)*?";
    
    private static Pattern operator;
    private static Matcher operatorMatches;
    
    private static final String OperatorRegex ="([+]|[-]|[/]|[*]|[&]|[=]|[<]|[>]|[!])";
    
    private static Pattern CiftOperator;
    private static Matcher CiftOperatorMatches;
    
    private static final String CıftOperatorRegex ="([a-zA-Z]*?[+]{2}[a-zA-Z]*?)|([a-zA-Z]*?[-]{2}[a-zA-Z]*?)|([a-zA-Z]*?[=]{2}[a-zA-Z]*?)|([a-zA-Z]*?[&]{2}[a-zA-Z]*?)|([a-zA-Z]*?[|]{2}[a-zA-Z]*?)";
   
     private static Pattern AdiBolme;
    private static Matcher AdiBolmeMatches;
    
    private static final String AdiBolmeRegex="((|,|))";
    
    public static int fonksiyonSayisi=0;
    public static int fonksiyonlar=0;
    public static int parametreSayisi=0;
    public static int operatorSayisi=0;
    public static String parametreler;
    public static  int y=0;
    public static String parametresi="";
    public static String Fonksiyonu="";
    public static String okunanlar="";
    
    /*Burada bize verilen dosyayı okuduk ve okuduğumuz her satırı konrol edilip istenilen hale getirilmesi için konrol fonksiyonuna
    parametre olarak verdik.*/
     static void Okuma()throws FileNotFoundException, IOException 
    {
        File file = new File("Program.c");
        BufferedReader reader ;
        reader = new BufferedReader(new FileReader(file));
        
        String satir = reader.readLine();
  
        while (satir!=null)
            {
                  
                    Kontrol(satir);
                    satir = reader.readLine();
                    
            }
   
        reader.close();
    }
      
     /* Okuma fonksiyonundan aldığımız satırı fonksiyon parametre ve operatör sayıları adları için ilgili fonksiyonlarımıza parametre olarak verdik.*/
       static void Kontrol(String okunan)
    {
        FonksiyonKontrol(okunan);
        OperatorKontrol(okunan);
    }
       /*Fonksiyon adı ve parametre adı olarak bulduğumuz ifadelerdeki gereksiz ifadelerimizi çıkarmak için yazılmış bir fonksiyondur.*/
     static String AdiBulma(String ad)
    {
        
                 if(ad.contains(","))
                 {
                  ad=ad.replace(",","");
               
                 }
                 else if(ad.contains("("))
                 {
                  ad=ad.replace("(","");
               
                 }
                 else if(ad.contains(")"))
                 {
                      ad=ad.replace(")","");
                
                 }
                 return ad;
             
}
  
   /*Kontrol fonksiyonundan aldığımız satırımızı regex ifademiz ile karşılaştırıp match olması durumunda fonksiyon sayımızı arttırdık ve
     fonskiyonun olması durumu parametre olması durumunu da doğurduğu için parmaetreKonrol fonksiyonumuzu çağırdık.
     Daha sonra match.group ile eşleşen stringimizi adiBulma fonskiyonumuza parametre olarak verdik ve bize adı doğru bir şekilde döndürdü.
     Ardından bulduğumuz ifadeyi fonskiyonlar değişkenimize ekledik*/
     
    static void FonksiyonKontrol(String okunan)
    {
         fonksiyon = Pattern.compile(FonksiyonRegex);
         fonksiyonMatches = fonksiyon.matcher(okunan);
         fonksiyonadi = Pattern.compile(FonksiyonAdiRegex);
         fonksiyonadiMatches = fonksiyonadi.matcher(okunan);
         
            if(fonksiyonMatches.matches())
            {
                    fonksiyonSayisi++;                    
                    ParametreKontrol(okunan);
                     
                    if(fonksiyonadiMatches.find()){
                        
                        Fonksiyonu=AdiBulma(fonksiyonadiMatches.group())+"-Parametreler:";
                       
                    }
                    
                  okunanlar+=(Fonksiyonu+parametresi+"\n");
                   
                    
            }
    
    
    }
    /*Fonksiyon kontrol fonksiyonumuzdan aldığımız string ifadeyi alıp parametre regex ifademizle karşılaştırıp parametre olup olmasını,
    sayısını ve adi bulma fonksiyonumuza vererek parametre adlarımızı bulduk.Ardından parametler değişkenimize ekledik.
    */
    static void ParametreKontrol(String okunan)
    {
        parametresi="";
        parametre = Pattern.compile(ParametreSayisiRegexi);
        parametreMatcher = parametre.matcher(okunan);
        
            while(parametreMatcher.find()) {
                 
                parametreSayisi++;
                  
                parametresi+=(AdiBulma(parametreMatcher.group())+" ") ;
                     
                }
         
                  
    }
    /*
    Kontrol fonksiyonumdan aldığım satırları regex ifadelerim ile kontrol ederek eşleşen durumlarda 1 arttırıyoruz.
    Fakat daha sonra çift operatör olması durumunda operatör sayımızı 1 azaltıyoruz.Böylelikle operatör sayımızı buluyoruz.
    */
    static void OperatorKontrol(String okunan)
    {
            operator = Pattern.compile(OperatorRegex);
            operatorMatches = operator.matcher(okunan);
           
            CiftOperator = Pattern.compile(CıftOperatorRegex);
            CiftOperatorMatches = CiftOperator.matcher(okunan);
                    
             
            while(operatorMatches.find())
                {
                    operatorSayisi++;
                   
                    while(CiftOperatorMatches.find())
                    {
                        operatorSayisi --;

                    }
                    
                    }
    }
/*
    Burada var olan bilgilerimizi yazdırıyoruz.
    */
    static void Yazdir()
    {
    System.out.println(" Toplam operatör sayısı:"+operatorSayisi);
    System.out.println(" Toplam fonksiyon sayısı:"+fonksiyonSayisi);
    System.out.println(" Toplam parametre sayısı:"+parametreSayisi);
    System.out.println(" Fonksiyon isimleri:");
    System.out.println(okunanlar);
    
    }
 public static void main(String[] args)throws FileNotFoundException, IOException 
    {
      Okuma();
      Yazdir();
      
    }
     
}
