import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Colony {
    private Integer id;
        private Integer hiveId;
        private String colonyOrigin;
        private String queenBreed;
        private Integer queenYear;
        private Integer numberOfSupers;
        private Integer numberOfFrames;
        private Integer numberOfBees;
        private Integer numberOfBrood;
        private Integer kgHoney;
        private Integer pollen;
        private String varroaTreatment;
        private Integer foodAdded;
        public Colony() {
        }
//    ARGUMENT CONSTRUCTOR
        public Colony(Integer id, Integer hiveId, String colonyOrigin, String queenBreed, Integer queenYear, Integer numberOfSupers, Integer numberOfFrames, Integer numberOfBees, Integer numberOfBrood, Integer kgHoney, Integer pollen, String varroaTreatment, Integer foodAdded) {
            this.id = id;
            this.hiveId = hiveId;
            this.colonyOrigin = colonyOrigin;
            this.queenBreed = queenBreed;
            this.queenYear = queenYear;
            this.numberOfSupers = numberOfSupers;
            this.numberOfFrames = numberOfFrames;
            this.numberOfBees = numberOfBees;
            this.numberOfBrood = numberOfBrood;
            this.kgHoney = kgHoney;
            this.pollen = pollen;
            this.varroaTreatment = varroaTreatment;
            this.foodAdded = foodAdded;
        }
    // CREATE METHOD TO ADD NEW COLONY

    public static void addColony(Colony colony, Scanner scanner, Hive hive, History history, Location
            location, LocalDateTime localDateTime, DateTimeFormatter dateTimeFormatter, Methods methods, DbConnection dbConnection) {
        do {
            System.out.println("Select hive_id where you want to add this colony: ");
            colony.setHiveId(methods.validatorForIntegers(scanner));

            if (colony.getHiveId() == dbConnection.thereIsColonyWithThisHiveNr(colony)) {
                System.out.println("There is colony in this hive already, please choose another one");
            }
            if (colony.getHiveId() != dbConnection.isThereHiveWithThisNr(hive, colony)) {
                System.out.println("There is no hive with this nr, please choose what would you like to do: ");
                int menu3;
                do {
                    System.out.println("to create new hive enter 1");
                    System.out.println("to chose another hive enter 0");
                    menu3 = methods.validatorForIntegers(scanner);

                    switch (menu3) {
                        default:
                            if (menu3 != 0) {
                                System.out.println("This menu item does not exist");
                                System.out.println("Please double check the number you have entered!");
                            }
                            break;
                        case 1:
                            hive.addNewHive(scanner, hive, history, location, localDateTime, dateTimeFormatter, methods, dbConnection);
                    }
                    if (menu3 == 1) {
                        break;
                    }

                } while (menu3 != 0);

            }
        }
        while (colony.getHiveId() == dbConnection.thereIsColonyWithThisHiveNr(colony)
                || colony.getHiveId() != dbConnection.isThereHiveWithThisNr(hive, colony));

        System.out.println("Please enter colony origin: ");
        colony.setColonyOrigin(scanner.nextLine());
        System.out.println("Please enter queen breed: ");
        colony.setQueenBreed(scanner.nextLine());
        System.out.println("Please enter queen year: ");
        colony.setQueenYear(methods.validatorForIntegers(scanner));
        System.out.println("Please enter the number of supers: ");
        colony.setNumberOfSupers(methods.validatorForIntegers(scanner));
        System.out.println("Please enter the number of frames: ");
        colony.setNumberOfFrames(methods.validatorForIntegers(scanner));
        System.out.println("Please enter the number of bees: ");
        colony.setNumberOfBees(methods.validatorForIntegers(scanner));
        System.out.println("Please enter the number of brood: ");
        colony.setNumberOfBrood(methods.validatorForIntegers(scanner));
        System.out.println("Please enter the amount of honey: ");
        colony.setKgHoney(methods.validatorForIntegers(scanner));
        System.out.println("Please enter the number of pollen frames: ");
        colony.setKgHoney(methods.validatorForIntegers(scanner));
        System.out.println("Please enter the varroa treatment: ");
        colony.setVarroaTreatment(scanner.nextLine());
        System.out.println("Please enter the amount of added food");
        colony.setFoodAdded(methods.validatorForIntegers(scanner));
        dbConnection.createColony(colony);
        history.setActionDone("Added new colony to hive nr.: " + colony.getHiveId() + " in location: " + dbConnection.findLocationForAddedHive(location, hive));
        history.setDataAndTime(localDateTime.format(dateTimeFormatter));
        dbConnection.addRecordToHistory(history);
        System.out.println("Added new colony to: " + colony.getHiveId() + "hive in location: " + dbConnection.findLocationForAddedHive(location, hive));
    }
//case 4 methods for ADD
    //ADD FOOD
    public static void addFoodAdded(Scanner scanner, Colony colony, History history, LocalDateTime
            localDateTime, DateTimeFormatter dateTimeFormatter, Methods methods, DbConnection dbConnection) {
        System.out.println("Enter hive id ");
        colony.setHiveId(methods.validatorForIntegers(scanner));
        System.out.println("Add food ");
        colony.setFoodAdded(methods.validatorForIntegers(scanner));
        dbConnection.attachFoodAdded(colony);
        // ADD TO HISTORY
        history.setActionDone("Added food " + colony.getFoodAdded() + " in hive nr. " + colony.getHiveId());
        history.setDataAndTime(localDateTime.format(dateTimeFormatter));
        dbConnection.addRecordToHistory(history);
    }
    //ADD VARROA TREATMENT
    public static void addVarroaTreatment(Scanner scanner, Colony colony, History history, LocalDateTime
            localDateTime, DateTimeFormatter dateTimeFormatter, Methods methods, DbConnection dbConnection) {
        System.out.println("Enter hive id ");
        colony.setHiveId(methods.validatorForIntegers(scanner));
        System.out.println("Add varroa treatment ");
        colony.setVarroaTreatment(scanner.nextLine());
        dbConnection.attachVarroaTreatment(colony);
        // ADD TO HISTORY
        history.setActionDone("Added varroa treatment " + colony.getVarroaTreatment() + " in hive nr. " + colony.getHiveId());
        history.setDataAndTime(localDateTime.format(dateTimeFormatter));
        dbConnection.addRecordToHistory(history);
    }

    //ADD POLLEN
    public static void addPollen(Scanner scanner, Colony colony, History history, LocalDateTime
            localDateTime, DateTimeFormatter dateTimeFormatter, Methods methods, DbConnection dbConnection) {
        System.out.println("Enter hive id ");
        colony.setHiveId(methods.validatorForIntegers(scanner));
        System.out.println("Add pollen ");
        colony.setPollen(methods.validatorForIntegers(scanner));
        dbConnection.attachPollen(colony);
        // ADD TO HISTORY
        history.setActionDone("Added pollen " + colony.getPollen() + " in hive nr. " + colony.getHiveId());
        history.setDataAndTime(localDateTime.format(dateTimeFormatter));
        dbConnection.addRecordToHistory(history);
    }
    //ADD KG OF HONEY
    public static void addKgHoney(Scanner scanner, Colony colony, History history, LocalDateTime
            localDateTime, DateTimeFormatter dateTimeFormatter, Methods methods, DbConnection dbConnection) {
        System.out.println("Enter hive id ");
        colony.setHiveId(methods.validatorForIntegers(scanner));
        System.out.println("Add kg of honey ");
        colony.setKgHoney(methods.validatorForIntegers(scanner));
        dbConnection.attachKgHoney(colony);
        // ADD TO HISTORY
        history.setActionDone("Added kg of honey " + colony.getKgHoney() + " in hive nr. " + colony.getHiveId());
        history.setDataAndTime(localDateTime.format(dateTimeFormatter));
        dbConnection.addRecordToHistory(history);
    }
    //ADD NUMBER OF BROOD
    public static void addNumOfBrood(Scanner scanner, Colony colony, History history, LocalDateTime
            localDateTime, DateTimeFormatter dateTimeFormatter, Methods methods, DbConnection dbConnection) {
        System.out.println("Enter hive id ");
        colony.setHiveId(methods.validatorForIntegers(scanner));
        System.out.println("Add number of brood ");
        colony.setNumberOfBrood(methods.validatorForIntegers(scanner));
        dbConnection.attachNumOfBrood(colony);
        // ADD TO HISTORY
        history.setActionDone("Added number of brood " + colony.getNumberOfBrood() + " in hive nr. " + colony.getHiveId());
        history.setDataAndTime(localDateTime.format(dateTimeFormatter));
        dbConnection.addRecordToHistory(history);
    }
    //ADD NUMBER OF BEES
    public static void addNumOfBees(Scanner scanner, Colony colony, History history, LocalDateTime
            localDateTime, DateTimeFormatter dateTimeFormatter, Methods methods, DbConnection dbConnection) {
        System.out.println("Enter hive id ");
        colony.setHiveId(methods.validatorForIntegers(scanner));
        System.out.println("Add number of bees ");
        colony.setNumberOfBees(methods.validatorForIntegers(scanner));
        dbConnection.attachNumOfBees(colony);
        // ADD TO HISTORY
        history.setActionDone("Added number of bees " + colony.getNumberOfBees() + " in hive nr. " + colony.getHiveId());
        history.setDataAndTime(localDateTime.format(dateTimeFormatter));
        dbConnection.addRecordToHistory(history);
    }
    //ADD NUMBER OF FRAMES
    public static void addNumOfFrames(Scanner scanner, Colony colony, History history, LocalDateTime
            localDateTime, DateTimeFormatter dateTimeFormatter, Methods methods, DbConnection dbConnection) {
        System.out.println("Enter hive id ");
        colony.setHiveId(methods.validatorForIntegers(scanner));
        System.out.println("Add number of frames ");
        colony.setNumberOfFrames(methods.validatorForIntegers(scanner));
        dbConnection.attachNumOfFrames(colony);
        // ADD TO HISTORY
        history.setActionDone("Added number of frames " + colony.getNumberOfFrames() + " in hive nr. " + colony.getHiveId());
        history.setDataAndTime(localDateTime.format(dateTimeFormatter));
        dbConnection.addRecordToHistory(history);
    }
    //ADD NUMBER OF SUPERS
    public static void addNumOfSupers(Scanner scanner, Colony colony, History history, LocalDateTime
            localDateTime, DateTimeFormatter dateTimeFormatter, Methods methods, DbConnection dbConnection) {
        System.out.println("Enter hive id ");
        colony.setHiveId(methods.validatorForIntegers(scanner));
        System.out.println("Add number of supers ");
        colony.setNumberOfSupers(methods.validatorForIntegers(scanner));
        dbConnection.attachNumOfSupers(colony);
        // ADD TO HISTORY
        history.setActionDone("Added number of supers " + colony.getNumberOfSupers() + " in hive nr. " + colony.getHiveId());
        history.setDataAndTime(localDateTime.format(dateTimeFormatter));
        dbConnection.addRecordToHistory(history);
    }
    //ADD QUEEN YEAR
    public static void addQueenYear(Scanner scanner, Colony colony, History history, LocalDateTime
            localDateTime, DateTimeFormatter dateTimeFormatter, Methods methods, DbConnection dbConnection) {
        System.out.println("Enter hive id ");
        colony.setHiveId(methods.validatorForIntegers(scanner));
        System.out.println("Add queen's year ");
        colony.setQueenYear(methods.validatorForIntegers(scanner));
        dbConnection.attachQueenYear(colony);
        // ADD TO HISTORY
        history.setActionDone("Added queen's year " + colony.getQueenYear() + " in hive nr. " + colony.getHiveId());
        history.setDataAndTime(localDateTime.format(dateTimeFormatter));
        dbConnection.addRecordToHistory(history);
    }
    // ADD QUEEN BREED
    public static void addQueenBreed(Scanner scanner, Colony colony, History history, LocalDateTime
            localDateTime, DateTimeFormatter dateTimeFormatter, Methods methods, DbConnection dbConnection) {
        System.out.println("Enter hive id ");
        colony.setHiveId(methods.validatorForIntegers(scanner));
        System.out.println("Add queen breed ");
        colony.setQueenBreed(scanner.nextLine());
        dbConnection.attachQueenBreed(colony);
        // ADD TO HISTORY
        history.setActionDone("Added queen breed " + colony.getQueenBreed() + " in hive nr. " + colony.getHiveId());
        history.setDataAndTime(localDateTime.format(dateTimeFormatter));
        dbConnection.addRecordToHistory(history);
    }
    //ADD COLONY ORIGIN
    public static void addColonyOrigin(Scanner scanner, Colony colony, History history, LocalDateTime
            localDateTime, DateTimeFormatter dateTimeFormatter, Methods methods, DbConnection dbConnection) {
        System.out.println("Enter hive id ");
        colony.setHiveId(methods.validatorForIntegers(scanner));
        System.out.println("Add colony's origin ");
        colony.setColonyOrigin(scanner.nextLine());
        dbConnection.attachColonyOrigin(colony);
        // ADD TO HISTORY
        history.setActionDone("Added colony origin " + colony.getColonyOrigin() + " in hive nr. " + colony.getHiveId());
        history.setDataAndTime(localDateTime.format(dateTimeFormatter));
        dbConnection.addRecordToHistory(history);
    }
    //HOW MANY COLONIES ARE TREATED WITH VARROA TREATMENT,SORT BY COUNT
    public static void varroaTreatmentCount(Colony colony, DbConnection dbConnection) {
        dbConnection.varroaTreatmentCount(colony);
    }
    //  3 STRONGEST COLONIES BY HONEY
    public static void strongestHivesByHoney(Colony colony, Location location, Hive hive, DbConnection dbConnection) {
        dbConnection.strongestColoniesByHoney(colony, hive, location);
    }
    //    SUM OF HONEY AND POLLEN
    public static void honeyAndPollenSum(Colony colony, DbConnection dbConnection) {
        dbConnection.sumOfHoneyAndPollen(colony);
    }
//    TO STRING METHOD

        @Override
        public String toString() {
            return "Colony{" +
                    "id=" + id +
                    ", hiveId=" + hiveId +
                    ", colonyOrigin='" + colonyOrigin + '\'' +
                    ", queenBreed='" + queenBreed + '\'' +
                    ", queenYear='" + queenYear + '\'' +
                    ", numberOfSupers=" + numberOfSupers +
                    ", numberOfFrames=" + numberOfFrames +
                    ", numberOfBees=" + numberOfBees +
                    ", numberOfBrood=" + numberOfBrood +
                    ", kgHoney=" + kgHoney +
                    ", pollen=" + pollen +
                    ", varroaTreatment='" + varroaTreatment + '\'' +
                    ", foodAdded=" + foodAdded +
                    '}';
        }


//    GETTERS & SETTERS


        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getHiveId() {
            return hiveId;
        }

        public void setHiveId(Integer hiveId) {
            this.hiveId = hiveId;
        }

        public String getColonyOrigin() {
            return colonyOrigin;
        }

        public void setColonyOrigin(String colonyOrigin) {
            this.colonyOrigin = colonyOrigin;
        }

        public String getQueenBreed() {
            return queenBreed;
        }

        public void setQueenBreed(String queenBreed) {
            this.queenBreed = queenBreed;
        }

        public Integer getQueenYear() {
            return queenYear;
        }

        public void setQueenYear(Integer queenYear) {
            this.queenYear = queenYear;
        }

        public Integer getNumberOfSupers() {
            return numberOfSupers;
        }

        public void setNumberOfSupers(Integer numberOfSupers) {
            this.numberOfSupers = numberOfSupers;
        }

        public Integer getNumberOfFrames() {
            return numberOfFrames;
        }

        public void setNumberOfFrames(Integer numberOfFrames) {
            this.numberOfFrames = numberOfFrames;
        }

        public Integer getNumberOfBees() {
            return numberOfBees;
        }

        public void setNumberOfBees(Integer numberOfBees) {
            this.numberOfBees = numberOfBees;
        }

        public Integer getNumberOfBrood() {
            return numberOfBrood;
        }

        public void setNumberOfBrood(Integer numberOfBrood) {
            this.numberOfBrood = numberOfBrood;
        }

        public Integer getKgHoney() {
            return kgHoney;
        }

        public void setKgHoney(Integer kgHoney) {
            this.kgHoney = kgHoney;
        }

        public Integer getPollen() {
            return pollen;
        }

        public void setPollen(Integer pollen) {
            this.pollen = pollen;
        }

        public String getVarroaTreatment() {
            return varroaTreatment;
        }

        public void setVarroaTreatment(String varroaTreatment) {
            this.varroaTreatment = varroaTreatment;
        }

        public Integer getFoodAdded() {
            return foodAdded;
        }

        public void setFoodAdded(Integer foodAdded) {
            this.foodAdded = foodAdded;
        }
    }
