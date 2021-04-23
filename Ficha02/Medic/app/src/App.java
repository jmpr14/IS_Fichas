import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.DataTypeException;
import ca.uhn.hl7v2.model.v24.datatype.*;
import ca.uhn.hl7v2.model.v24.message.ORM_O01;
import ca.uhn.hl7v2.model.v24.segment.*;
import ca.uhn.hl7v2.parser.Parser;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HapiContext;


public class App {

    public static void main(String[] args) {

        String pass = "";

        HapiContext context = new DefaultHapiContext();
        Parser pipeParser = context.getPipeParser();

        try {
            FileReader arq = new FileReader("configs.txt");
            BufferedReader lerArq = new BufferedReader(arq);
            pass = lerArq.readLine();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        DataBase bd = new DataBase(pass);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int op = -1;

        System.out.println("\n-------------------------------------------------------------");
        System.out.println("------------------------- MEDIC APP -------------------------");
        System.out.println("-------------------------------------------------------------");

        while (op != 0) {

            System.out.println("\n> INDIQUE O NÚMERO DA OPERAÇÃO PRETENDIDA:");
            System.out.println("0 - Sair.");
            System.out.println("1 - Ver Pedidos.");
            System.out.println("2 - Ver Relatório.");
            System.out.println("3 - Realizar exame.");

            try {
                op = Integer.parseInt(reader.readLine());

                switch (op) {
                    case 0:
                        System.out.println("\nA SAIR!");
                        break;
                    case 1:
                        bd.showPedidos();
                        break;
                    case 2:
                        System.out.println("-- Insira o número do Pedido de Exame do qual pretende consultar o Relatório.");
                        String id_Pedido = reader.readLine();
                        bd.showRelatorio(id_Pedido);
                        break;
                    case 3:
                        System.out.println("-- Insira o número do Pedido de Exame a realizar.");
                        String num_Pedido = reader.readLine();
                        System.out.println("---- Insira o número de Episódio da Consulta a iniciar.");
                        String num_ep = reader.readLine();
                        if (bd.realizarPedido(num_Pedido,num_ep)){
                            System.out.println("Exame em curso!");
                            System.out.println("---- Insira o Relatório do Exame realizado.");
                            String relatorio = reader.readLine();
                            bd.insertRelatorio(num_Pedido,relatorio);
                            bd.insertWorklist(Integer.parseInt(num_Pedido),1);
                            System.out.println("Exame Realizado com sucesso!");

                            String id_doente = bd.getIdDoenteIdPedido(num_Pedido);
                            String numUtente = bd.getNumUtenteIdDoente(id_doente);

                            String nome = bd.getNomeIdDoente(numUtente);
                            String morada = bd.getMoradaIdDoente(numUtente);
                            String telefone = bd.getTelefoneIdDoente(numUtente);
                            String descricao = bd.getDescricaoIdPedido(num_Pedido);
                            String id_exame = bd.getIDExameIDPedido(num_Pedido);
                            String siglaExame = bd.getSiglaExameIDExame(id_exame);
/*
                            System.out.println("id_doente: "+id_doente + "\nnumUtente: " + numUtente + "\nnum_Pedido: "
                                    + num_Pedido + "\nnome: " + nome + "\nmorada: " + morada + "\n telefone " +
                                    telefone + "\ndescricao: " + descricao + "\n sigla: " + siglaExame);*/

                            ORM_O01 _adtMessage;
                            _adtMessage = Build(numUtente, num_Pedido, num_ep, nome, morada, telefone, descricao, siglaExame, relatorio);
                            writeMessageToFile(pipeParser, _adtMessage, "./logs/"+num_Pedido+".txt");
                        }
                        else System.out.println("Operação inválida!");
                        break;
                    default:
                        System.out.println("\nInsira uma operação válida!");
                        break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }


    }


    public static ORM_O01 Build(String numPaciente, String id_pedido, String num_ep, String nome, String morada, String telefone,
                                String descricao, String siglaExame, String relatorio)
            throws HL7Exception, IOException {
        String currentDateTimeString = getCurrentTimeStamp();
        ORM_O01 _ormMessage = new ORM_O01();
        //you can use the context class's newMessage method to instantiate a message if you want
        _ormMessage.initQuickstart("ORM", "O01", "P");
        createMshSegment(_ormMessage,currentDateTimeString);
        createPidSegment(_ormMessage, numPaciente, nome, morada, telefone);
        //createPv1Segment(_ormMessage);
        createORCSegment(_ormMessage, id_pedido, num_ep);
        createOBRSegment(_ormMessage, descricao, id_pedido, siglaExame);
        createOBX1segment(_ormMessage, siglaExame);
        createOBX2segment(_ormMessage);

        String[] tokens = relatorio.split("\\.");
        int i = 2;
        for(String token : tokens){
            createOBXisegment(_ormMessage, i, token);
            i++;
        }
        return _ormMessage;
    }

    public static void createMshSegment(ORM_O01 t,String currentDateTimeString) throws DataTypeException {
        MSH mshSegment = t.getMSH();
        mshSegment.getFieldSeparator().setValue("|");
        mshSegment.getEncodingCharacters().setValue("^~\\&");
        mshSegment.getSendingApplication().getNamespaceID().setValue("desk_medic");
        mshSegment.getSendingFacility().getNamespaceID().setValue("desk_medic");
        mshSegment.getReceivingApplication().getNamespaceID().setValue("desk_services");
        mshSegment.getReceivingFacility().getNamespaceID().setValue("desk_services");
        mshSegment.getDateTimeOfMessage().getTimeOfAnEvent().setValue(currentDateTimeString);
        mshSegment.getMessageControlID().setValue(getSequenceNumber());
        mshSegment.getVersionID().getVersionID().setValue("2.4");
    }

    public static void createPidSegment(ORM_O01 t, String numPaciente, String nome, String morada, String telefone) throws DataTypeException {
        PID pid = t.getPATIENT().getPID();
        XPN patientName = pid.getPatientName(0);
        XTN patientTele = pid.getPhoneNumberHome(0);
        patientTele.getPhoneNumber().setValue(telefone);
        //patientName.getFamilyName().getSurname().setValue("Mouse");
        patientName.getGivenName().setValue(nome);
        pid.getPatientIdentifierList(0).getID().setValue(numPaciente);
        XAD patientAddress = pid.getPatientAddress(0);
        patientAddress.getStreetAddress().getStreetOrMailingAddress().setValue(morada);
        //patientAddress.getCity().setValue("Lake Buena Vista");
        patientAddress.getStateOrProvince().setValue("PT");
        patientAddress.getCountry().setValue("PT");
    }

    public static void createPv1Segment(ORM_O01 t) throws DataTypeException {
        PV1 pv1 = t.getPATIENT().getPATIENT_VISIT().getPV1();
        pv1.getPatientClass().setValue("I"); // O to represent an 'Outpatient'
        /*PL assignedPatientLocation = pv1.getAssignedPatientLocation();
        assignedPatientLocation.getFacility().getNamespaceID().setValue("Some Treatment Facility Name");
        assignedPatientLocation.getPointOfCare().setValue("Some Point of Care");
        pv1.getAdmissionType().setValue("ALERT");
        XCN referringDoctor = pv1.getReferringDoctor(0);
        referringDoctor.getIDNumber().setValue("99999999");
        referringDoctor.getFamilyName().getSurname().setValue("Smith");
        referringDoctor.getGivenName().setValue("Jack");
        referringDoctor.getIdentifierTypeCode().setValue("456789");*/
        pv1.getAdmitDateTime().getTimeOfAnEvent().setValue(getCurrentTimeStamp());
    }

    public static void createORCSegment(ORM_O01 t, String id_pedido, String num_ep) throws DataTypeException{
        ORC orc = t.getORDER().getORC();
        orc.getOrc1_OrderControl().setValue("RE");
        //String id_pedidoS = Integer.toString(id_pedido);
        orc.getOrc2_PlacerOrderNumber().getNamespaceID().setValue(id_pedido);
        orc.getOrc3_FillerOrderNumber().getNamespaceID().setValue(num_ep);
        orc.getOrc9_DateTimeOfTransaction().getTimeOfAnEvent().setValue(getCurrentTimeStamp());
        orc.getOrc10_EnteredBy(0).getAssigningAuthority().getNamespaceID().setValue("desk_medic");
        orc.getOrc11_VerifiedBy(0).getAssigningAuthority().getNamespaceID().setValue("desk_medic");
        orc.getOrc15_OrderEffectiveDateTime().getTimeOfAnEvent().setValue(getCurrentTimeStamp());
        orc.getOrc19_ActionBy(0).getAssigningAuthority().getNamespaceID().setValue("desk_medic");
    }

    public static void createOBRSegment(ORM_O01 t, String descricao, String id_pedido, String siglaExame) throws DataTypeException{
        OBR obr = t.getORDER().getORDER_DETAIL().getOBR();
        //String id_pedidoS = Integer.toString(id_pedido);
        obr.getObr1_SetIDOBR().setValue(id_pedido);
        obr.getPlacerOrderNumber().getNamespaceID().setValue("4727374");
        obr.getFillerOrderNumber().getNamespaceID().setValue("4727374");
        obr.getObr13_RelevantClinicalInfo().setValue(descricao);
        obr.getObr44_ProcedureCode().getAlternateIdentifier().setValue(siglaExame);

    }

    public static void createOBX1segment(ORM_O01 t, String siglaExame) throws DataTypeException {
        OBX obx = t.getORDER().getORDER_DETAIL().getOBSERVATION(0).getOBX();
        obx.getObx1_SetIDOBX().setValue("1");
        obx.getObx2_ValueType().setValue("TX");
        obx.getObx3_ObservationIdentifier().getCe1_Identifier().setValue(siglaExame);
        obx.getObx11_ObservationResultStatus().setValue("F");
        obx.getObx19_DateTimeOfTheAnalysis().getTimeOfAnEvent().setValue(getCurrentTimeStamp());
    }


    public static void createOBX2segment(ORM_O01 t) throws DataTypeException {
        OBX obx = t.getORDER().getORDER_DETAIL().getOBSERVATION(1).getOBX();
        obx.getObx1_SetIDOBX().setValue("2");
        obx.getObx2_ValueType().setValue("TX");
        obx.getObx3_ObservationIdentifier().getCe1_Identifier().setValue("RELATORIO:");
        obx.getObx11_ObservationResultStatus().setValue("F");
        obx.getObx19_DateTimeOfTheAnalysis().getTimeOfAnEvent().setValue(getCurrentTimeStamp());
    }

    public static void createOBXisegment(ORM_O01 t, int i, String relatorio) throws DataTypeException {
        OBX obx = t.getORDER().getORDER_DETAIL().getOBSERVATION(i).getOBX();
        int j = i+1;
        obx.getObx1_SetIDOBX().setValue(String.valueOf(j));
        obx.getObx2_ValueType().setValue("TX");
        obx.getObx3_ObservationIdentifier().getCe1_Identifier().setValue(relatorio);
        obx.getObx11_ObservationResultStatus().setValue("F");
        obx.getObx19_DateTimeOfTheAnalysis().getTimeOfAnEvent().setValue(getCurrentTimeStamp());
    }

    public static String getCurrentTimeStamp() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    public static String getSequenceNumber() {
        String facilityNumberPrefix = "1234"; // some arbitrary prefix for the facility
        return facilityNumberPrefix.concat(getCurrentTimeStamp());
    }

    private static void writeMessageToFile(Parser parser, ORM_O01 adtMessage, String outputFilename)
            throws IOException, FileNotFoundException, HL7Exception {
        OutputStream outputStream = null;
        try {

            // Remember that the file may not show special delimiter characters when using
            // plain text editor
            File file = new File(outputFilename);

            // quick check to create the file before writing if it does not exist already
            if (!file.exists()) {
                file.createNewFile();
            }

            System.out.println("Serializing message to file...");
            outputStream = new FileOutputStream(file);
            outputStream.write(parser.encode(adtMessage).getBytes());
            outputStream.flush();

            System.out.printf("Message serialized to file '%s' successfully", file);
            System.out.println("\n");
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }
}
