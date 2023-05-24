function B()

%% parameter setting
% input parameter
D=10:10:100;
center_D=[10,50,80];

V=10:10:100;
center_V=[20,60,80];

SoR=10:10:100;
center_SoR=[20 50 90];

% output parameter
Num_Output=10; 
center_Output=[0 10 20 30 40 50 60 70 80 90 100]; 

label=0;  %label==0 Minmun; label==1 Prod;

%%

%task 1: for a wet state,  the dependency of F on D and v
span=[30,30,30]; %1:D 2:V 3:SoR
center=[center_D',center_V',center_SoR'];  %1:D 2:V 3:SoR
G=Get(D,V,SoR,span,center,label);
o=G*[40 60 90 20 40 70 10 20 30]'./sum(G')';
%show figure;
count=1;
for i=1:length(o)
    if mod(i,length(o)/(length(D)*length(V)))==0
        result(count)=sum(o(i+1-length(o)/(length(D)*length(V)):i));
        count=count+1;
    end
end

figure;
surf(reshape(result,length(D),length(V)));
title('the dependency of F on D and V');
xlabel('D');
ylabel('V');

%task 2: for a medium velocity,  the dependency of F on D and SoR
span=[30,30,30]; %1:D 2:SoR 3:V
center=[center_D',center_SoR',center_V'];  %1:D 2:SoR 3:V
G=Get(D,SoR,V,span,center,label);
o=G*[90 60 40 70 40 20 30 20 10]'./sum(G')';
%show figure;
count=1;
for i=1:length(o)
    if mod(i,length(o)/(length(D)*length(SoR)))==0
        result(count)=sum(o(i+1-length(o)/(length(D)*length(SoR)):i));
        count=count+1;
    end
end

figure;
surf(reshape(result,length(D),length(SoR)));
title('the dependency of F on D and SoR');
xlabel('D');
ylabel('SoR');

%task 3: for a medium distance,  the dependency of F on V and SoR
span=[30,30,30]; %1:V 2:SoR 3:D
center=[center_V',center_SoR',center_D'];  %1:V 2:SoR 3:D
G=Get(V,SoR,D,span,center,label);
o=G*[30 20 10 70 40 20 90 60 40]'./sum(G')';
%show figure;
count=1;
for i=1:length(o)
    if mod(i,length(o)/(length(V)*length(SoR)))==0
        result(count)=sum(o(i+1-length(o)/(length(V)*length(SoR)):i));
        count=count+1;
    end
end

figure;
surf(reshape(result,length(V),length(SoR)));
title('the dependency of F on V and SoR');
xlabel('V');
ylabel('SoR');

