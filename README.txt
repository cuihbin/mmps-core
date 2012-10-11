This is the base framework helps setting up a distributed application that sending and receiving
message between a central point and multiple clients spread over the network.

MMPS is the original codename of the project refers to 'Multi-Media Publishing System'.



mmps-core       Parent of the following modules

mmps-console    Enable application to output log messsage to several destination without
                knowledge to each of them. A single console is a virtual destination that log 
                messages are output to. An implementation of console writing logs to file is
                provided by default.
  
mmps-app        Define application's properties like name and version, and provide facilities
                such as startup/shutdown.
  
mmps-task       Task is the basic execution unit with lifecycle events support like initializing 
                and destroying. 
  
mmps-gui        A console implementation provides GUI for log message display
  
mmps-jms        Handling JMS message sending and receiving
  
mmps-scheduler  Provides scheduler support