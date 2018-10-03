package io.github.craciuncezar.infobac.utils;


import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtils {

  public static String getDateString(Date date){
    Date currentDate = new Date();
    long diff = currentDate.getTime() - date.getTime();

    long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
    long hours = TimeUnit.MILLISECONDS.toHours(diff);
    long days = TimeUnit.MILLISECONDS.toDays(diff);
    long months = days/30;
    long years = months/12;

    if(years!=0){
      String aniSauAn = years > 1 ? " ani" : " an";
      return "• acum " + years + aniSauAn;
    } else if(months!=0){
      String luniSauLuna = months > 1 ? " luni" : " luna";
      return "• acum " + months + luniSauLuna;
    } else if(days!=0){
      String zileSauZi = days > 1 ? " zile" : " zi";
      return "• acum " + days + zileSauZi;
    } else if(hours!=0) {
      String oreSauOra = hours > 1 ? " ore" : " ora";
      return "• acum " + hours + oreSauOra;
    } else if(minutes!=0){
      String minuteSauMinut = minutes > 1 ? " minute" : " minut";
      return "• acum " + minutes + minuteSauMinut;
    } else {
      return "• acum";
    }
  }
}
