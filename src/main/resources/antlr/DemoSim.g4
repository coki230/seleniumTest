grammar DemoSim;
suanshu : NUMBER OP NUMBER ;
NUMBER : [0-9]+;
OP : ('jia' | 'jie');
WS : [ \t\r\n]+ -> skip;