/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atn.mtnng.ussdagent.utilities;

/**
 *
 * @author manzi
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author aubain
 */
public class Log {
    public static final void error(Class className, Throwable e){
        if(className != null){
            Logger logger = LogManager.getLogger(className.getCanonicalName());
            logger.error("Error",e);
        }
        
    }
    public static final void error(Class className, String message){
        if(message != null && className != null){
            Logger logger = LogManager.getLogger(className.getCanonicalName());
            logger.error(message);
        }
    }
    public static final void trace(Class className, String trace){
        if(className != null){
            Logger logger = LogManager.getLogger(className.getCanonicalName());
            logger.trace(trace);
        }
        
    }
    public static final void trace(Class className, Throwable e){
        if(className != null){
            Logger logger = LogManager.getLogger(className.getCanonicalName());
            logger.trace(e);
        }
    }
    public static final void fatal(Class className, String fatal){
        if(className != null){
            Logger logger = LogManager.getLogger(className.getCanonicalName());
            logger.fatal(fatal);
        }
        
    }
    public static final void fatal(Class className, Throwable e){
        if(className != null){
            Logger logger = LogManager.getLogger(className.getCanonicalName());
            logger.fatal(e);
        }
    }
    public static final void info(Class className, String message){
        if(message != null && className != null){
            Logger logger = LogManager.getLogger(className.getCanonicalName());
            logger.info(message.replace("\n", ""));
        }
    }    
}