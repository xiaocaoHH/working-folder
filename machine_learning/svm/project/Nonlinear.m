function Nonlinear()
%%
clear all;
%read dataset;
load vote.mat;

X=scale(X);
[numb_data,dim] = size(X);
Y(find(Y==2))=-1;
tol = 1e-12;

%%
%cross validation

kernel = 1; % 1 is Gauss, 2 is Poly
fold=10;
C=[1e-4 1e-3 1e-2 1e-1 1e0 1e1];

if kernel == 1
	parameters=[0.5 1 2 5 15];
else
	parameters=[1 2 3];
end

%shuffle
rand('seed',1)
ind = randint(1,numb_data,numb_data);
X=X(ind,:);
Y=Y(ind);
sizetest = floor(numb_data/fold);

for iC=1:length(C)
	c=C(iC);
    for iParam=1:length(parameters)
        SumError=0;
        
        for n=1:fold
			
            if n<fold
			   indtest = (n-1)*sizetest+1:n*sizetest;
			   indtrain=setdiff(1:numb_data,indtest);
            else
			   indtest = (n-1)*sizetest+1:numb_data;
			   indtrain=setdiff(1:numb_data,indtest);							
            end
            
			XX=X(indtrain,:);
            YY=Y(indtrain);
            
%%
            %create Hessian matrices;            
			if kernel == 1                
			   s=parameters(iParam);
               sig=s*ones(1,dim);
               G=grbf(XX,XX,sig);               
            else
			   order = parameters(iParam);
			   if order == 1
				  G = (XX*XX');
               else
				  G = (XX*XX' + 1).^order;		
			   end
			end
							
            H=(YY*YY').*G;
            H=H+1e-8*eye(size(H));
            
            %find a;
            len=length(YY);
            f=ones(len,1);
            A=YY';
            B=0;
            VLB=zeros(len,1);
            VUB=c*ones(len,1);
            X0=zeros(1,len);

            Alphas=qp5(H,-f,A,B,VLB,VUB,X0,1);
            
            %calculate bias
            indSV = find(Alphas > tol);			
			indSVfree = find(Alphas > tol & Alphas ~= c);
            b = sum(YY(indSVfree)-G(indSVfree,indSVfree)*(Alphas(indSVfree).*YY(indSVfree)))/length(indSVfree);
            
            %calculate error
			Xtest=X(indtest,:);
            Ytest=Y(indtest);

			if kernel == 1
			   s=parameters(iParam);
               sig=s*ones(1,dim);
               G=grbf(Xtest,XX(indSV,:),sig);
            else
			   order = parameters(iParam);
			   if order == 1
				  G = (Xtest*XX(indSV,:)');
               else
				  G = (Xtest*XX(indSV,:)' + 1).^order;
			   end
            end
            
            result=sign(G*(Alphas(indSV).*YY(indSV))+b);       
            ErrorP = length(find(result-Ytest));
            SumError=ErrorP+SumError;
        end
        
        %store result;
        Error_matrix(iParam,iC) = SumError/numb_data
    end
end

%get best parameter
       
str='Best parameter:';
disp(str);
[row,col]=find(Error_matrix==min(min(Error_matrix)));
c=C(col)

if kernel == 1
	s=parameters(row)
else
	d=parameters(row)
end


