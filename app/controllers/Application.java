package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;

import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.*;

import views.html.index;
import views.html.demo;
import views.html.grid.demo1;
import views.html.grid.demo2;
import views.html.grid.shorting;
import views.html.grid.comp_nom;
import views.html.grid.empleados_nom;


import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;


public class Application extends Controller {

    public static Result index() {

        return ok(index.render("Que"));
    }

    public static Result demo() {

        return ok(demo.render("Demo chavo"));

    }


    public static Result demo_grid_json() {

        return ok(demo1.render("Demo grid json"));
    }

    public static Result demo_grid_json1() {

        return ok(demo2.render("Demo grid con datos de empleado"));
    }


    public static Result get_empleado() {

        String query= "SELECT\n"+
                "  persona.persona_nom AS nombre,\n"+
                "  persona.persona_mat apellido_materno,\n"+
                "  persona.persona_pat AS apellido_paterno,\n"+
                "  TO_CHAR(persona.persona_nac,'DD/MM/YYYY' ) fecha_nacimiento,\n"+
                "  empsueldo.es_sueldo\n"+
                "FROM\n"+
                "  tpersona persona,\n"+
                "  templeado empleado,\n"+
                "  tempstatus empstatus,\n"+
                "  tempnomina empnomina,\n"+
                "  tempsueldo empsueldo\n"+
                "WHERE\n"+
                "  persona.persona_cve    = empleado.persona_cve\n"+
                "AND empleado.emp_cve     = empstatus.emp_cve\n"+
                "AND empleado.cia_cve     = empstatus.cia_cve\n"+
                "AND empleado.emp_cve     = empnomina.emp_cve\n"+
                "AND empleado.cia_cve     = empnomina.cia_cve\n"+
                "AND empleado.emp_cve     = empsueldo.emp_cve\n"+
                "AND empleado.cia_cve     = empsueldo.cia_cve\n"+
                "AND empsueldo.es_fecfin IS NULL\n"+
                "AND empstatus.empsts_ult = 'S'\n"+
                "AND empnomina.empnom_ult = 'S'\n"+
                "AND empleado.cia_cve     =11002008";

        List<SqlRow> persona= Ebean.createSqlQuery(query ).findList();

        return ok(Json.toJson(persona));
    }
    public static Result demo_grid_shorting() {


        return ok(shorting.render("Demo grid con datos de empleado"));
    }

    public static Result nom_comp() {

        return ok(comp_nom.render("hola"));


    }

    public static Result get_empleado2(){

        DynamicForm requestData = Form.form().bindFromRequest();

        String id_nomina=requestData.get("id_nom");

        String query= "SELECT\n"+
                "  persona.persona_nom AS nombre,\n"+
                "  persona.persona_mat apellido_materno,\n"+
                "  persona.persona_pat AS apellido_paterno,\n"+
                "  TO_CHAR(persona.persona_nac,'DD/MM/YYYY' ) fecha_nacimiento,\n"+
                "  empsueldo.es_sueldo\n"+
                "FROM\n"+
                "  tpersona persona,\n"+
                "  templeado empleado,\n"+
                "  tempstatus empstatus,\n"+
                "  tempnomina empnomina,\n"+
                "  tempsueldo empsueldo\n"+
                "WHERE\n"+
                "  persona.persona_cve    = empleado.persona_cve\n"+
                "AND empleado.emp_cve     = empstatus.emp_cve\n"+
                "AND empleado.cia_cve     = empstatus.cia_cve\n"+
                "AND empleado.emp_cve     = empnomina.emp_cve\n"+
                "AND empleado.cia_cve     = empnomina.cia_cve\n"+
                "AND empleado.emp_cve     = empsueldo.emp_cve\n"+
                "AND empleado.cia_cve     = empsueldo.cia_cve\n"+
                "AND empsueldo.es_fecfin IS NULL\n"+
                "AND empstatus.empsts_ult = 'S'\n"+
                "AND empnomina.empnom_ult = 'S'\n"+
                "AND empnomina.nom_cve     = :nom_cve";

        List<SqlRow> persona= Ebean.createSqlQuery(query ).
                setParameter("nom_cve",id_nomina ).
                findList();

        return ok(Json.toJson(persona));

    }

   public static Result empleados_nom() {

       String id_cia =  "7001001";
       String id_cve ="110010001";

       String query = "SELECT nom.NOM_CVE,\n"+
               "               nom.NOM_NOM\n"+
               "       FROM tusuxnom usuxnom,\n"+
               "               tnomina nom"+
               "\n"+
               "      WHERE usuxnom.nom_cve = nom.NOM_CVE\n"+
               "      and   usuxnom.CU_CVE = :id_cve\n"+
               "      and   nom.CIA_CVE = :id_cia";


       List<SqlRow> persona= Ebean.createSqlQuery(query ).
               setParameter("id_cve", id_cve).
               setParameter("id_cia", id_cia).
          findList();


       Map<String,String> HashMap=new HashMap<>();

       for (SqlRow sqlRow: persona) {
           HashMap.put(sqlRow.getString("nom_cve"), sqlRow.getString("nom_cve") + " - " +
                   sqlRow.getString("nom_nom"));


       }



      String query2=  "SELECT area.area_CVE,\n"+
              "      area.area_nom\n"+
              " FROM tusuxarea usuxarea,\n"+
              " tareas area \n"+
              " WHERE usuxarea.cia_cve = area.cia_cve\n"+
              " AND usuxarea.AREA_CVE  = area.area_cve\n"+
              " AND usuxarea.CU_CVE    = :id_cve\n"+
              " AND area.CIA_CVE       = :id_cia\n"+
              "ORDER BY \n"+
              "AREA.AREA_NOM asc";

       List<SqlRow> empleado= Ebean.createSqlQuery(query2 ).
               setParameter("id_cve", id_cve).
               setParameter("id_cia", id_cia).
               findList();

       Map<String,String> hasaraea=new HashMap<>();

       for(SqlRow aux:empleado){
           hasaraea.put(aux.getString("area_cve"), aux.getString("area_nom"));


       }


       return ok(empleados_nom.render( HashMap,hasaraea ));
   }


//        public static Result get_emleado3() {
//            DynamicForm requestData = Form.form().bindFromRequest();
//
//            String cu_cve=requestData.get("id_nom");
//            String cu_cve=requestData.get("id_area");
//
//
//            String query = "  SELECT\n" +
//                    "              E1.EMP_CVE NUMERO,\n" +
//                    "              E1.EMP_CVECLIENTE CLAVE,\n" +
//                    "              P1.PERSONA_PAT PATERNO,\n" +
//                    "              P1.PERSONA_MAT MATERNO,\n" +
//                    "              P1.PERSONA_NOM NOMBRE,\n" +
//                    "              DECODE(P1.PERSONA_SEX,'F','FEMENINO','M','MASCULINO','NO CAPTURADO') SEXO, \n" +
//                    "              DECODE(P1.EDOCIVIL_CVE,1,'SOLTERO',2,'CASADO','NO CAPTURADO') CIVIL,\n" +
//                    "              TO_CHAR(P1.PERSONA_NAC,'DD/MM/YYYY') NACIMIENTO,\n" +
//                    "              P1.PERSONA_RFC RFC,\n" +
//                    "              P1.PERSONA_CURP CURP,\n" +
//                    "              P1.PERSONA_IMSS IMSS,\n" +
//                    "              P1.PERSONA_CARTILLA CARTILLA,\n" +
//                    "              P1.PERSONA_PASAPORTE PASAPORTE,\n" +
//                    "              P1.PERSONA_EMAIL EMAIL,\n" +
//                    "              D.DIRPER_CALLENUM  DOM_CALLENUM,\n" +
//                    "              D.DIRPER_NUMEXT DOM_NUMEXT,\n" +
//                    "              D.DIRPER_NUMINT  DOM_NUMINT,\n" +
//                    "              D.DIRPER_TEL TELEFONO,\n" +
//                    "              D.DIRPER_MOVIL MOVIL,\n" +
//                    "              CP.CP_CP DOM_CP,\n" +
//                    "              CP.CP_ASENTAMIENTO DOM_COLONIA,\n" +
//                    "              C.CIUDAD_NOM DOM_CDMUN,\n" +
//                    "              E.EST_NOM ESTADO,\n" +
//                    "        (SELECT \n" +
//                    "                            DECODE(ESC.ESCPER_NIVEL,1,'PRIMARIA',2,'SECUNDARIA',3,'PREPARATORIA',4,'VOCACIONAL',5,'TÃCNICA',6,'LICENCIATURA',7,'MAESTRIA',8,'DOCTORADO')\n" +
//                    "                            FROM \n" +
//                    "                            TESCPERSONA ESC\n" +
//                    "                            where \n" +
//                    "                            ESC.PERSONA_CVE = E1.PERSONA_CVE\n" +
//                    "                            AND ESC.ESCPER_NIVEL = (SELECT MAX(ESCPER_NIVEL) FROM TESCPERSONA WHERE PERSONA_CVE = E1.PERSONA_CVE) AND ROWNUM<2) GRADO,\n" +
//                    "                          (SELECT \n" +
//                    "                           ESP.CESPE_NOM \n" +
//                    "                            FROM \n" +
//                    "                            TESCPERSONA ESC,\n" +
//                    "                            TESCCARESPE ESP\n" +
//                    "                            where \n" +
//                    "                            ESC.PERSONA_CVE = E1.PERSONA_CVE\n" +
//                    "                            AND ESC.ESCPER_NIVEL = (SELECT MAX(ESCPER_NIVEL) FROM TESCPERSONA WHERE PERSONA_CVE = E1.PERSONA_CVE) AND\n" +
//                    "                            ESC.CESPE_CVE = ESP.CESPE_CVE AND ROWNUM<2\n" +
//                    "                            ) CARRERA,\n" +
//                    "                          (SELECT \n" +
//                    "                           ESCPER_INSTITUCION\n" +
//                    "                            FROM \n" +
//                    "                            TESCPERSONA ESC\n" +
//                    "                            where \n" +
//                    "                            ESC.PERSONA_CVE = E1.PERSONA_CVE\n" +
//                    "                            AND ESC.ESCPER_NIVEL = (SELECT MAX(ESCPER_NIVEL) FROM TESCPERSONA WHERE PERSONA_CVE = E1.PERSONA_CVE) AND ROWNUM<2) INSTITUCION,\n" +
//                    "                             (SELECT \n" +
//                    "                           ESCPER_LUGAR\n" +
//                    "                            FROM \n" +
//                    "                            TESCPERSONA ESC\n" +
//                    "                            where \n" +
//                    "                            ESC.PERSONA_CVE = E1.PERSONA_CVE\n" +
//                    "                            AND ESC.ESCPER_NIVEL = (SELECT MAX(ESCPER_NIVEL) FROM TESCPERSONA WHERE PERSONA_CVE = E1.PERSONA_CVE) AND ROWNUM<2) LUGAR,\n" +
//                    "                             (SELECT \n" +
//                    "                           ESCPER_GMC\n" +
//                    "                            FROM \n" +
//                    "                            TESCPERSONA ESC\n" +
//                    "                            where \n" +
//                    "                            ESC.PERSONA_CVE = E1.PERSONA_CVE\n" +
//                    "                            AND ESC.ESCPER_NIVEL = (SELECT MAX(ESCPER_NIVEL) FROM TESCPERSONA WHERE PERSONA_CVE = E1.PERSONA_CVE) AND ROWNUM<2) GRADO_MAX,\n" +
//                    "                             (SELECT \n" +
//                    "                          DECODE(ESC.ESCPER_TITULADO,'S','SI','N','NO')\n" +
//                    "                            FROM \n" +
//                    "                            TESCPERSONA ESC\n" +
//                    "                            where \n" +
//                    "                            ESC.PERSONA_CVE = E1.PERSONA_CVE\n" +
//                    "                            AND ESC.ESCPER_NIVEL = (SELECT MAX(ESCPER_NIVEL) FROM TESCPERSONA WHERE PERSONA_CVE = E1.PERSONA_CVE) AND ROWNUM<2) TITULADO,\n" +
//                    "              S1.CIA_CVE CIA,\n" +
//                    "              CIA.CIA_NOM  NOMBRECIA,\n" +
//                    "              NOM.NOM_NOM DESCRIP_NOM,\n" +
//                    "              ENOM.NOM_CVE NOMINA,\n" +
//                    "              E1.EMP_CUENTA NOCUENTA, \n" +
//                    "              BAN.BANCO_NOM BANCO,\n" +
//                    "              (SELECT CMPTIPOCON_NOM FROM TTIPOCONTRATO_CMP WHERE CMPTIPOCON_CVE=CONT.CMPTIPOCON_CVE AND PAIS_CVE=1) CONTRATO,\n" +
//                    "              DECODE(CONT.CMPTIPOCON_CVE,2,TO_CHAR(EMPCONTMP_FECVEN,'DD/MM/YYYY'),NULL) FEC_TERMINO,\n" +
//                    "              TO_CHAR(ANTXEMP_ANTIG,'DD/MM/YYYY') ANTIGUEDAD,\n" +
//                    "              TO_CHAR(S1.EMPSTS_FECINI,'DD/MM/YYYY') DE_FECALTA,\n" +
//                    "              TO_CHAR(S1.EMPSTS_FECINI ,'DD/MM/YYYY') FECINI,\n" +
//                    "              TO_CHAR(S1.EMPSTS_FECFIN,'DD/MM/YYYY') FEC_BAJA,\n" +
//                    "              DECODE(NVL(TO_CHAR(S1.EMPSTS_FECFIN),'A'),'A','ACTIVO','BAJA') STATUS, \n" +
//                    "              LAB.POLLAB_CVE NIVEL,\n" +
//                    "              SU.ES_SUELDO SUELDO,\n" +
//                    "              BA.MOTBAJA_NOM,\n" +
//                    "              S1.EMPSTS_DESCRIP EMPSTS_DESCRIP,\n" +
//                    "              S1.EMPSTS_ULT EMPSTS_ULT,\n" +
//                    "              S1.EMPSTS_BAJAEXT EMPSTS_BAJAEXT,\n" +
//                    "              E1.BANCO_CVE,\n" +
//                    "              E1.EMP_TIPOPAGO,\n" +
//                    "              PL1.PLAZA_CVE PLAZA_CVE,\n" +
//                    "              TO_CHAR(PL1.PLAZA_FECFIN,'DD/MM/YYYY') PLAZA_FECFIN,\n" +
//                    "              DECODE( PL1.PLAZA_TIPO,'T','TEMPORAL','P','PERMANENTE')TIPOPLAZA,\n" +
//                    "              PUE.PUESTO_CVE CLAVEPUESTO,\n" +
//                    "              PUE.PUESTO_NOM PUESTO,\n" +
//                    "              DECODE(E1.EMP_TIPOPAGO,'D','DEPOSITO','C','CHEQUE','E','EFECTIVO')FORMA,\n" +
//                    "              CEN.CENCOS_CVE CENTRO,\n" +
//                    "              CEN.CENCOS_NOM CENTRO_DESCRIP,\n" +
//                    "        (SELECT \n" +
//                    "           PER.PERSONA_PAT||' '||PER.PERSONA_MAT||' '||PER.PERSONA_NOM\n" +
//                    "           FROM \n" +
//                    "           TEMPLEADO EMP,\n" +
//                    "           TPERSONA PER,\n" +
//                    "           TEMPPLAZA EPL,\n" +
//                    "           TEMPSTATUS STS\n" +
//                    "           WHERE \n" +
//                    "           EMP.PERSONA_CVE=PER.PERSONA_CVE AND\n" +
//                    "           EMP.CIA_CVE=E1.CIA_CVE AND\n" +
//                    "           STS.EMP_CVE=EMP.EMP_CVE AND\n" +
//                    "           STS.CIA_CVE= EMP.CIA_CVE AND\n" +
//                    "           STS.EMPSTS_FECFIN IS NULL AND\n" +
//                    "           STS.EMPSTS_ULT='S' AND\n" +
//                    "           EPL.EMP_CVE=EMP.EMP_CVE AND\n" +
//                    "           EPL.CIA_CVE=EMP.CIA_CVE AND\n" +
//                    "           EPL.EP_ULT='S' AND  \n" +
//                    "           EPL.PLAZA_CVE=PL1.PLAZA_CVESUP \n" +
//                    "           )JEFE_DIRECTO, \n" +
//                    "         E1.REGPAT_REGISTRO \n" +
//                    "          FROM\n" +
//                    "               IGENTER.TEMPSTATUS S1,\n" +
//                    "               IGENTER.TPERSONA P1,\n" +
//                    "               IGENTER.TEMPLEADO E1,\n" +
//                    "               IGENTER.TEMPPLAZA EP1,\n" +
//                    "               IGENTER.TPLAZA PL1,\n" +
//                    "               IGENTER.TEMPNOMINA ENOM,\n" +
//                    "               IGENTER.TNOMINA NOM,\n" +
//                    "               IGENTER.TCOMPANIA CIA,\n" +
//                    "               IGENTER.TBANCO BAN,\n" +
//                    "               IGENTER.TANTXEMP ANT,\n" +
//                    "               IGENTER.TPOLITICALAB LAB,\n" +
//                    "               IGENTER.TEMPSUELDO SU,\n" +
//                    "               IGENTER.TDIRPERSONA D,\n" +
//                    "               IGENTER.TCODIGOPOSTAL CP,\n" +
//                    "               IGENTER.TESTADO E,\n" +
//                    "               IGENTER.TCIUDAD C,\n" +
//                    "               IGENTER.TASENTAMIENTO A,\n" +
//                    "               IGENTER.TPUESTO PUE,\n" +
//                    "               IGENTER.TCENTROCOSTOS CEN,\n" +
//                    "               IGENTER.TUSUXAREA UXA,\n" +
//                    "               IGENTER.TUSUXNOM UXN,\n" +
//                    "               IGENTER.TMOTIVOBAJA BA,\n" +
//                    "               IGENTER.TEMPXCONTCMP CONT\n" +
//                    "          WHERE\n" +
//                    "           P1.PERSONA_CVE=S1.PERSONA_CVE\n" +
//                    "          AND E1.PERSONA_CVE=S1.PERSONA_CVE\n" +
//                    "          AND EP1.CIA_CVE = E1.CIA_CVE\n" +
//                    "          AND PL1.CIA_CVE = EP1.CIA_CVE\n" +
//                    "          AND EP1.CIA_CVE = CONT.CIA_CVE(+)\n" +
//                    "          AND EP1.PLAZA_CVE =PL1.PLAZA_CVE\n" +
//                    "          AND EP1.EMP_CVE = E1.EMP_CVE\n" +
//                    "          AND EP1.EMP_CVE = CONT.EMP_CVE(+)\n" +
//                    "          AND CONT.EMPCONTMP_ULT = 'S'\n" +
//                    "          AND EP1.EP_ULT ='S'\n" +
//                    "          AND S1.CIA_CVE = E1.CIA_CVE\n" +
//                    "          AND S1.EMP_CVE=E1.EMP_CVE\n" +
//                    "          AND ENOM.EMP_CVE=E1.EMP_CVE\n" +
//                    "          AND ENOM.CIA_CVE= E1.CIA_CVE\n" +
//                    "          AND ENOM.EMPNOM_ULT='S'\n" +
//                    "          AND NOM.NOM_CVE=ENOM.NOM_CVE\n" +
//                    "          AND NOM.CIA_CVE=ENOM.CIA_CVE\n" +
//                    "          AND CIA.CIA_CVE= E1.CIA_CVE\n" +
//                    "          AND BAN.BANCO_CVE(+) = E1.BANCO_CVE\n" +
//                    "          AND BAN.PAIS_CVE(+) = 1  \n" +
//                    "          AND ANT.CIA_CVE= E1.CIA_CVE\n" +
//                    "          AND ANT.EMP_CVE = E1.EMP_CVE\n" +
//                    "          AND ANT.ANTXEMP_ULT = 'S'\n" +
//                    "          AND ANT.PERSONA_CVE=E1.PERSONA_CVE\n" +
//                    "          AND LAB.CIA_CVE = E1.CIA_CVE\n" +
//                    "          AND LAB.POLLAB_CVE = PL1.POLLAB_CVE\n" +
//                    "          AND SU.CIA_CVE=E1.CIA_CVE\n" +
//                    "          AND SU.EMP_CVE=E1.EMP_CVE\n" +
//                    "          AND SU.ES_FECINI = (SELECT MAX(ES_FECINI) FROM IGENTER.TEMPSUELDO S WHERE S.CIA_CVE=E1.CIA_CVE\n" +
//                    "          AND S.EMP_CVE=E1.EMP_CVE  )\n" +
//                    "          AND D.PERSONA_CVE(+) = P1.PERSONA_CVE\n" +
//                    "          AND CP.CP_CVE(+)=D.CP_CVE \n" +
//                    "          AND E.EST_CVE(+)=CP.EST_CVE\n" +
//                    "          AND C.CIUDAD_CVE(+)=CP.CIUDAD_CVE\n" +
//                    "          AND A.ASENTA_CVE(+)= CP.CP_ASENTAMIENTO\n" +
//                    "          AND PL1.PUESTO_CVE=PUE.PUESTO_CVE\n" +
//                    "          AND PUE.CIA_CVE= E1.CIA_CVE\n" +
//                    "          AND PL1.PUESTO_CVE=PUE.PUESTO_CVE  \n" +
//                    "          AND PL1.CENCOS_CVE=CEN.CENCOS_CVE \n" +
//                    "          AND PL1.CENCOS_CVE=CEN.CENCOS_CVE\n" +
//                    "          AND CEN.CIA_CVE= E1.CIA_CVE\n" +
//                    "          AND UXA.CIA_CVE=E1.CIA_CVE\n" +
//                    "          AND UXA.AREA_CVE=PL1.AREA_CVE\n" +
//                    "          AND UXN.NOM_CVE=NOM.NOM_CVE \n" +
//                    "          AND BA.MOTBAJA_CVE(+)=S1.MOTBAJA_CVE \n" +
//                    "          AND BA.PAIS_CVE(+)= 1 \n" +
//                    "          AND UXA.CU_CVE=" cu_cve + "               \n" +
////                    "          AND UXN.CU_CVE=" +cu_cve+" \n"+
////                    wherefec + wherenomina + wherearea+
////                    "          AND E1.CIA_CVE="+cia_cve+"  \n"+
////                    "          ORDER BY ENOM.NOM_CVE,ENOM.EMP_CVE ";
//
////
//                    dbResultSet rs = xdb.executeQuery(query);
//            //         return rs;
//
//            //                List<SqlRow> persona= Ebean.createSqlQuery(query ).
//            //                setParameter("nom_cve",id_nomina ).
////                    findList();
//
////            return ok(Json.toJson(persona))


//    List<SqlRow> nominas = Ebean.createSqlQuery(query)
//            .setParameter("cveEmpleado", DatosEmpleado.getPersona_cve())
//            .findList();
//
//    return  Json.toJson(nominas);
//
//        }
}
