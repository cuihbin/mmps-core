This is the base framework helps setting up a distributed application that sending and
receiving message between a central point and multiple clients spread over the network.

MMPS is the original codename of the project refers to 'Multi-Media Publishing System'.



mmps-core       Parent of the following modules

mmps-console    Console is virtual destination that log messages are output to.

mmps-app        Define application's properties like name and version, and provide facilities
                such as startup/shutdown.

mmps-task       Task is the basic execution unit with life cycle events support like
                initializing and destroying.

mmps-gui        A console implementation provides GUI for log message display

mmps-jms        Handling JMS message sending and receiving

mmps-scheduler  Provides scheduler support