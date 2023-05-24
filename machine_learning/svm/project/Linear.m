function Linear()
%%
clear all;

%read dataset;
load vote.mat;
[numb_data,dim] = size(X);
X = scale(X);
Y(find(Y==2)) = -1;

%%
%cross validation
fold = 10;
C = [1e-4 1e-3 1e-2 1e-1 1e0 1e1];
tol = 1e-12;

%shuffle
rand('seed',1)
ind = randint(1,numb_data,numb_data);
X = X(ind,:);
Y = Y(ind);
sizetest = floor(numb_data/fold);

%main code
for iC = 1:length(C)
	c = C(iC);
    SumError=0;
    
    for n=1:fold
        
		if n<fold
		   indtest = (n-1)*sizetest+1:n*sizetest;
		   indtrain = setdiff(1:numb_data,indtest);
        else
		   indtest = (n-1)*sizetest+1:numb_data;
		   indtrain = setdiff(1:numb_data,indtest);							
        end
        
		XX=X(indtrain,:);
        YY=Y(indtrain);
        
        %create Hessian matrices;
        H = (YY*YY').*(XX*XX');
        H = H+1e-8*eye(size(H));
    
        %find a;
        len = length(YY);
        f = ones(len,1);
        A = YY';
        B = 0;
        VLB = zeros(len,1);
        VUB = c*ones(len,1);
        X0 = zeros(1,len);

        Alphas = qp5(H,-f,A,B,VLB,VUB,X0,1);
    
        %calculate w
        indSV = find(Alphas>tol);
	    indSVfree = find(Alphas>tol & Alphas~=c);
        w=Alphas(indSV)'.*YY(indSV)'*XX(indSV,:);    
    
        %calculate b;
        b=sum(1./YY(indSVfree)-XX(indSVfree,:)*w')/length(indSVfree);
    
        %calculate error
        Xtest=X(indtest,:);
        Ytest=Y(indtest);
        
        result=sign(Xtest*w'+b);       
        ErrorP=length(find(result-Ytest)); 
        SumError=ErrorP+SumError;
    end
    
    %store result;
    Error_vector(iC)=SumError/numb_data
end

%get best parameter
       
str='Best parameter:';
disp(str);
c=C(Error_vector==min(Error_vector))
    
