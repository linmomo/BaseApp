package com.sixun.basework.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import android.text.TextUtils;

/**
 * 日历日期相关工具类
 * 
 * @author Administrator
 * 
 */
public class DateUtils {

	/**
	 * 日期类型 *
	 * 年:y	月:M	 日:d	  时:h(12制)/H(24值) 分:m	秒:s	毫秒:S
	 */
	public static final String yyyyMMDD = "yyyy-MM-dd";
	public static final String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
	public static final String HHmmss = "HH:mm:ss";
	public static final String hhmmss = "hh:mm:ss";
	public static final String LOCALE_DATE_FORMAT = "yyyy年M月d日 HH:mm:ss";
	public static final String DB_DATA_FORMAT = "yyyy-MM-DD HH:mm:ss";
	public static final String NEWS_ITEM_DATE_FORMAT = "hh:mm M月d日 yyyy";

	private DateUtils() {
	}

	/**
	 * 获取日期格式为2015-09-16
	 * 
	 * @param year
	 * @param monthOfYear
	 * @param dayOfMonth
	 * @return
	 */
	public static String getDate(int year, int monthOfYear, int dayOfMonth) {
		StringBuffer sb = new StringBuffer();
		sb.append(year);
		sb.append("-");
		if (monthOfYear < 9) {
			sb.append("0");
		}
		sb.append(monthOfYear + 1);
		sb.append("-");
		if (dayOfMonth < 10) {
			sb.append("0");
		}
		sb.append(dayOfMonth);
		return sb.toString();
	}
	
	/**
	 * 获取09：25格式时间
	 * 
	 * @param hourOfDay
	 * @param minute
	 * @return
	 */
	public static String getTime(int hourOfDay, int minute) {
		StringBuffer sb = new StringBuffer();
		if (hourOfDay < 10) {
			sb.append("0");
		}
		sb.append(hourOfDay);
		sb.append(":");
		if (minute < 10) {
			sb.append("0");
		}
		sb.append(minute);
		return sb.toString();
		
	}
	
	 /**
     * 得到当前系统时间和GMT时间(格林威治时间)1970年1月1号0时0分0秒所差的毫秒数 可以作为文件名 该时间点是唯一的
     * 
     * @return
     */
    public static long getCurrentTimeInLong() {
        return System.currentTimeMillis();
    }
    
    /**
     * 格式化毫秒时间为字符串 
     * 
     * @param timeInMillis
     * @param format
     * @return
     */
    public static String getTime(long timeInMillis, String format) {
        return new SimpleDateFormat(format,Locale.CHINA).format(new Date(timeInMillis));
    }
    
    /**
     * 格式化系统时间，格式为 yyyy-MM-dd HH:mm:ss
     * 
     * @return
     */
    public static String getCurrentTimeFormat() {
        return getTime(getCurrentTimeInLong(),yyyyMMddHHmmss);
    }
    
    /**
     * 根据格式化字符串格式化系统时间
     * @param format
     * @return
     */
    public static String getCurrentTimeFormat(String format) {
        return getTime(getCurrentTimeInLong(),format);
    }
    
	/**
	 * 格式化输出日期 
	 * 
	 * @param date
	 *            Date对象
	 * @param type
	 *            需要的日期格式
	 * @return 按照需求格式的日期字符串
	 */
	public static String Date2String(Date date, String type) {
		try {
			SimpleDateFormat df = new SimpleDateFormat(type, Locale.CHINA);
			return df.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 格式化解析日期文本 
	 * 
	 * @param dateStr
	 *            日期字符串
	 * @param type
	 *            日期字符串格式
	 * @return Date对象
	 */
	public static Date String2Date(String dateStr, String type) {
		SimpleDateFormat df = new SimpleDateFormat(type, Locale.CHINA);
		Date date = null;
		try {
			date = df.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;

	}

	/**
	 * 得到年
	 * 
	 * @param date
	 *            Date对象
	 * @return 年
	 */
	public static int getYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}

	/**
	 * 得到月
	 * 
	 * @param date
	 *            Date对象
	 * @return 月
	 */
	public static int getMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MONTH) + 1;

	}

	/**
	 * 得到日
	 * 
	 * @param date
	 *            Date对象
	 * @return 日
	 */
	public static int getDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 指定日期是否再系统日期之后
	 * @param mytime
	 * @return 
	 */
	public static boolean DateAfter(String mydate) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		Date sysdate, selectdate;
		try {
			sysdate = sdf.parse(sdf.format(new Date()));
			selectdate = sdf.parse(mydate);
			// 测试此日期是否在指定日期之后。
			boolean flag = selectdate.after(sysdate);
			if (flag) {
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 指定日期是否再系统日期前
	 * @param mytime
	 * @return
	 */
	public static boolean DateBefore(String mydate) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		Date sysdate, selectdate;
		try {
			sysdate = sdf.parse(sdf.format(new Date()));
			selectdate = sdf.parse(mydate);
			// 测试此日期是否在指定日期之前。
			boolean flag = selectdate.before(sysdate);
			if (flag) {
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;

	}
	
	/**
	 * 指定时间是否在当前时间前
	 * @param mytime
	 * @return
	 */
	public static boolean TimeBefore(String mytime) {

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.CHINA);
		Date systime, selecttime;
		try {
			systime = sdf.parse(sdf.format(new Date()));
			selecttime = sdf.parse(mytime);
			// 测试此日期是否在指定日期之前。
			boolean flag = selecttime.before(systime);
			if (flag) {
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 指定时间是否在当前时间后
	 * @param mytime
	 * @return
	 */
	public static boolean TimeAfter(String mytime) {

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.CHINA);
		Date systime, selecttime;
		try {
			systime = sdf.parse(sdf.format(new Date()));
			selecttime = sdf.parse(mytime);
			// 测试此日期是否在指定日期之后。
			boolean flag = selecttime.after(systime);
			if (flag) {
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 人性化时间显示
	 * @param date 时间字符串
	 * @return 格式化的时间
	 */
	public static String formatStringTime(String date){
		return formatDateTime(String2Date(date, yyyyMMddHHmmss));
	}
	
	/**
	 * 人性化时间显示
	 * @param date date时间对象
	 * @return 格式化的时间
	 */
	 public static String formatDateTime(Date date) {
	        String text;
	        long dateTime = date.getTime();
	        if (isSameDay(dateTime)) {
	            Calendar calendar = GregorianCalendar.getInstance();
	            //一分钟之内
	            if (inOneMinute(dateTime, calendar.getTimeInMillis())) {
	                return "刚刚";
	                //一小时之内
	            } else if (inOneHour(dateTime, calendar.getTimeInMillis())) {
	                return String.format("%d分钟之前", Math.abs(dateTime - calendar.getTimeInMillis()) / 60000);
	            } else {
	                calendar.setTime(date);
	                int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
	                //17点之后
	                if (hourOfDay > 17) {
	                    text = "晚上 hh:mm";
	                    //0到6点
	                } else if (hourOfDay >= 0 && hourOfDay <= 6) {
	                    text = "凌晨 hh:mm";
	                  //11点到17点
	                } else if (hourOfDay > 11 && hourOfDay <= 17) {
	                    text = "下午 hh:mm";
	                } else {
	                    text = "上午 hh:mm";
	                }
	            }
	        } else if (isYesterday(dateTime)) {
	            text = "昨天 HH:mm";
	        }else if(isBeforeYesterday(dateTime)){
	        	text = "前天 HH:mm";
	        }else if (isSameYear(dateTime)) {
	            text = "M月d日 HH:mm";
	        } else {
	            text = "yyyy-M-d HH:mm";
	        }

	        // 注意，如果使用android.text.format.DateFormat这个工具类，在API 17之前它只支持adEhkMmszy
	        return new SimpleDateFormat(text, Locale.CHINA).format(date);
	    }

	 	/**一分钟之内，刚刚*/
	    private static boolean inOneMinute(long time1, long time2) {
	        return Math.abs(time1 - time2) < 60000;
	    }
	    
	    /**一小时之内，刚刚*/
	    private static boolean inOneHour(long time1, long time2) {
	        return Math.abs(time1 - time2) < 3600000;
	    }

	    /**该时间是否为今天*/
	    private static boolean isSameDay(long time) {
	        long startTime = floorDay(Calendar.getInstance()).getTimeInMillis();
	        long endTime = ceilDay(Calendar.getInstance()).getTimeInMillis();
	        return time > startTime && time < endTime;
	    }

	    /**该时间是否为昨天*/
	    private static boolean isYesterday(long time) {
	        Calendar startCal;
	        startCal = floorDay(Calendar.getInstance());
	        startCal.add(Calendar.DAY_OF_MONTH, -1);
	        long startTime = startCal.getTimeInMillis();

	        Calendar endCal;
	        endCal = ceilDay(Calendar.getInstance());
	        endCal.add(Calendar.DAY_OF_MONTH, -1);
	        long endTime = endCal.getTimeInMillis();
	        return time > startTime && time < endTime;
	    }
	    
	    /**该时间是否为前天*/
	    private static boolean isBeforeYesterday(long time) {
	        Calendar startCal;
	        startCal = floorDay(Calendar.getInstance());
	        startCal.add(Calendar.DAY_OF_MONTH, -2);
	        long startTime = startCal.getTimeInMillis();

	        Calendar endCal;
	        endCal = ceilDay(Calendar.getInstance());
	        endCal.add(Calendar.DAY_OF_MONTH, -2);
	        long endTime = endCal.getTimeInMillis();
	        return time > startTime && time < endTime;
	    }

	    /**该时间是否为今年*/
	    private static boolean isSameYear(long time) {
	        Calendar startCal;
	        startCal = floorDay(Calendar.getInstance());
	        startCal.set(Calendar.MONTH, Calendar.JANUARY);
	        startCal.set(Calendar.DAY_OF_MONTH, 1);
	        return time >= startCal.getTimeInMillis();
	    }

	    /**设置时间为00：00：00：0*/
	    private static Calendar floorDay(Calendar startCal) {
	    	//将给定的日历字段设置为给定值。
	        startCal.set(Calendar.HOUR_OF_DAY, 0);
	        startCal.set(Calendar.MINUTE, 0);
	        startCal.set(Calendar.SECOND, 0);
	        startCal.set(Calendar.MILLISECOND, 0);
	        return startCal;
	    }

	    /**设置时间为23：59：59：999*/
	    private static Calendar ceilDay(Calendar endCal) {
	        endCal.set(Calendar.HOUR_OF_DAY, 23);
	        endCal.set(Calendar.MINUTE, 59);
	        endCal.set(Calendar.SECOND, 59);
	        endCal.set(Calendar.MILLISECOND, 999);
	        return endCal;
	    }
	    
		/**
		 * 时间格式转换 
		 * 获取传入时间和当前时间的差值
		 * */
		public static String formatdatatime(String time) {

			long days = 0;
			long hours = 0;
			long minutes = 0;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.CHINA);
			try {

				Date d1 = df.parse(time);
				long enterTime = System.currentTimeMillis();
				long diff = enterTime - d1.getTime();// 这样得到的差值是微秒级别
				days = diff / (1000 * 60 * 60 * 24);
				hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
				minutes = (diff - days * (1000 * 60 * 60 * 24) - hours* (1000 * 60 * 60))/ (1000 * 60);
			} catch (ParseException e) {
			}
			if (days > 0) {
				return "" + days + "天";
			} else if (hours > 0) {
				return "" + hours + "小时";
			} else {
				return "" + minutes + "分钟";
			}
		}

		/**
		 * 转换日期 将日期转为今天, 昨天, 前天, XXXX-XX-XX, ...
		 * 
		 * @param Date  格式-yyyy-MM-dd
		 *            日期
		 * @return 当前日期转换为更容易理解的方式
		 */
		public static String translateDate(Long time) {
			long oneDay = 24 * 60 * 60 * 1000;
			Calendar current = Calendar.getInstance();
			Calendar today = Calendar.getInstance(); // 今天

			// 将给定的日历字段设置为给定值。
			today.set(Calendar.YEAR, current.get(Calendar.YEAR));
			today.set(Calendar.MONTH, current.get(Calendar.MONTH));
			today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
			// Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
			today.set(Calendar.HOUR_OF_DAY, 0);
			today.set(Calendar.MINUTE, 0);
			today.set(Calendar.SECOND, 0);

			// 返回此 Calendar 的时间值，以毫秒为单位。
			long todayStartTime = today.getTimeInMillis();

			if (time >= todayStartTime && time < todayStartTime + oneDay) { // today
				return "今天";
			} else if (time >= todayStartTime - oneDay && time < todayStartTime) { // yesterday
				return "昨天";
			} else if (time >= todayStartTime - oneDay * 2
					&& time < todayStartTime - oneDay) { // the day before yesterday
				return "前天";
			} else if (time > todayStartTime + oneDay) { // future
				return "将来某一天";
			} else {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
				Date date = new Date(time);
				return dateFormat.format(date);
			}
		}

		/**
		 * 转换日期 转换为更为人性化的时间
		 * 
		 * @param time   格式化的时间
		 * @param curTime  对比时间
		 *            
		 * @return 返回的时间
		 */
		public static String translateDate(long time, long curTime) {
			long oneDay = 24 * 60 * 60;
			Calendar today = Calendar.getInstance(); // 今天
			today.setTimeInMillis(curTime * 1000);
			today.set(Calendar.HOUR_OF_DAY, 0);
			today.set(Calendar.MINUTE, 0);
			today.set(Calendar.SECOND, 0);
			long todayStartTime = today.getTimeInMillis() / 1000;
			if (time >= todayStartTime) {
				long d = curTime - time;
				if (d <= 60) {
					return "1分钟前";
				} else if (d <= 60 * 60) {
					long m = d / 60;
					if (m <= 0) {
						m = 1;
					}
					return m + "分钟前";
				} else {
					SimpleDateFormat dateFormat = new SimpleDateFormat("今天 HH:mm",Locale.CHINA);
					Date date = new Date(time * 1000);
					String dateStr = dateFormat.format(date);
					if (!TextUtils.isEmpty(dateStr) && dateStr.contains(" 0")) {
						dateStr = dateStr.replace(" 0", " ");
					}
					return dateStr;
				}
			} else {
				if (time < todayStartTime && time > todayStartTime - oneDay) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("昨天 HH:mm",Locale.CHINA);
					Date date = new Date(time * 1000);
					String dateStr = dateFormat.format(date);
					if (!TextUtils.isEmpty(dateStr) && dateStr.contains(" 0")) {

						dateStr = dateStr.replace(" 0", " ");
					}
					return dateStr;
				} else if (time < todayStartTime - oneDay&& time > todayStartTime - 2 * oneDay) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("前天 HH:mm",Locale.CHINA);
					Date date = new Date(time * 1000);
					String dateStr = dateFormat.format(date);
					if (!TextUtils.isEmpty(dateStr) && dateStr.contains(" 0")) {
						dateStr = dateStr.replace(" 0", " ");
					}
					return dateStr;
				} else {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.CHINA);
					Date date = new Date(time * 1000);
					String dateStr = dateFormat.format(date);
					if (!TextUtils.isEmpty(dateStr) && dateStr.contains(" 0")) {
						dateStr = dateStr.replace(" 0", " ");
					}
					return dateStr;
				}
			}
		}

}
