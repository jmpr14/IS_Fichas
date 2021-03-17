import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.DataTypeException;
import ca.uhn.hl7v2.model.v24.datatype.*;
import ca.uhn.hl7v2.model.v24.message.ADT_A01;
import ca.uhn.hl7v2.model.v24.message.ORM_O01;
import ca.uhn.hl7v2.model.v24.segment.*;
import ca.uhn.hl7v2.parser.Parser;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.v24.message.ADT_A01;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;


public class App {

    public static void main(String[] args) {

        String pass = "";
        try {
            FileReader arq = new FileReader("configs.txt");
            BufferedReader lerArq = new BufferedReader(arq);
            pass = lerArq.readLine();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        DataBase bd = new DataBase(pass);

        HapiContext context = new DefaultHapiContext();
        Parser pipeParser = context.getPipeParser();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int op = -1;

        System.out.println("\n--------------------------------------------------------------");
        System.out.println("------------------------ SERVICES APP ------------------------");
        System.out.println("--------------------------------------------------------------");

        while (op != 0) {

            System.out.println("\n> INDIQUE O NÚMERO DA OPERAÇÃO PRETENDIDA:");
            System.out.println("0 - Sair.");
            System.out.println("1 - Novo Pedido.");
            System.out.println("2 - Ver Pedidos.");
            System.out.println("3 - Ver Relatório.");
            System.out.println("4 - Cancelar Pedido.");

            try {
                op = Integer.parseInt(reader.readLine());

                switch (op) {
                    case 0:
                        System.out.println("\nA SAIR!");
                        break;
                    case 1:
                        System.out.println("-- Insira o número de Utente do paciente.");
                        String numUtente = reader.readLine();
                        String nome, morada, telefone;
                        int id_doente;
                        if (!bd.checkDoente(numUtente)) {
                            System.out.println("---- NOVO REGISTO DE DOENTE ----");
                            System.out.println("-- Insira o nome do paciente.");
                            nome = reader.readLine();
                            System.out.println("-- Insira a morada do paciente.");
                            morada = reader.readLine();
                            System.out.println("-- Insira o telefone do paciente.");
                            telefone = reader.readLine();

                            id_doente = bd.insertDoente(nome, telefone, numUtente, morada);
                        } else {
                            id_doente = bd.getIdDoente(numUtente);
                            nome = bd.getNomeIdDoente(numUtente);
                            morada = bd.getMoradaIdDoente(numUtente);
                            telefone = bd.getTelefoneIdDoente(numUtente);
                        }
                        System.out.println("-- Insira a sigla do tipo de Exame.");
                        String siglaExame = reader.readLine();
                        System.out.println("-- Insira uma descrição acerca do exame. (opcional)");
                        String descricao = reader.readLine();

                        int id_exame = bd.insertExame(siglaExame);
                        int id_pedido = bd.insertPedido(id_exame, id_doente, descricao);
                        bd.insertWorklist(id_pedido,0);

                        ORM_O01 _adtMessage;
                        _adtMessage = Build(numUtente, nome, morada, telefone, descricao);
                        writeMessageToFile(pipeParser, _adtMessage, numUtente+".txt");
                        break;
                    case 2:
                        bd.showPedidos();
                        break;
                    case 3:
                        System.out.println("-- Insira o número do Pedido do qual pretende consultar o Relatório.");
                        String id_Pedido = reader.readLine();
                        bd.showRelatorio(id_Pedido);
                    case 4:
                        System.out.println("-- Insira o número do Pedido a cancelar.");
                        String num_Pedido = reader.readLine();
                        if (bd.cancelarPedido(num_Pedido)){
                            System.out.println("Pedido Cancelado com sucesso!");
                            bd.insertWorklist(Integer.parseInt(num_Pedido),2);
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


    public static ORM_O01 Build(String numPaciente, String nome, String morada, String telefone, String descricao)
            throws HL7Exception, IOException {
        String currentDateTimeString = getCurrentTimeStamp();
        ORM_O01 _adtMessage = new ORM_O01();
        //you can use the context class's newMessage method to instantiate a message if you want
        _adtMessage.initQuickstart("ORM", "O01", "P");
        createMshSegment(_adtMessage,currentDateTimeString);
        createPidSegment(_adtMessage, numPaciente, nome, morada, telefone);
        createPv1Segment(_adtMessage);
        createOBRSegment(_adtMessage, descricao);
        return _adtMessage;
    }

    public static void createMshSegment(ORM_O01 t,String currentDateTimeString) throws DataTypeException {
        MSH mshSegment = t.getMSH();
        mshSegment.getFieldSeparator().setValue("|");
        mshSegment.getEncodingCharacters().setValue("^~\\&");
        mshSegment.getSendingApplication().getNamespaceID().setValue("Services");
        mshSegment.getSendingFacility().getNamespaceID().setValue("Services");
        mshSegment.getReceivingApplication().getNamespaceID().setValue("Medic");
        mshSegment.getReceivingFacility().getNamespaceID().setValue("Medic");
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

    public static void createOBRSegment(ORM_O01 t, String descricao) throws DataTypeException{
        OBR obr = t.getORDER().getORDER_DETAIL().getOBR();
        obr.getSetIDOBR().setValue("43");
        obr.getPlacerOrderNumber().getNamespaceID().setValue("7890");
        obr.getObr13_RelevantClinicalInfo().setValue(descricao);

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
