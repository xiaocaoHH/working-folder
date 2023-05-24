function A()

%%
x=0:0.1:2*pi;
y=sin(2*x).*exp(-0.5*x);

%% 
% input parameter
center_Input=[1,2,3,4,5,6]';

%%
% membership function parameter(1:triangle 2:guassian)
MF=1;
if MF==1
    span=1;     %scope of triangle membership function; 
    label=0;    %label==0 Minmun; label==1 Prod;
    G=GetDegree(x',center_Input,span,label);
else
    sig=0.5;
    G=grbf(x',center_Input,sig);
end
o=G*[0.6,-0.4,0.1,0.2,0,-0.1]'./sum(G')'; 

%%
%show figure;
plot(x,y,'r');
hold on;
plot(x,G,'b');
hold on;
plot(x,o,'g');