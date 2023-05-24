function SOL=SolveSCProblem(Bin)

%exclude 0 vector in Bin;
szBin=size(Bin);
row=find(sum(Bin')==0);
Bin(row,:)=[];

szBin=size(Bin);
ActiveMatrix=Bin;
szActiveMatrix=size(ActiveMatrix);
InactiveMatrix=[];
szInactiveMatrix=size(InactiveMatrix);

%initialization
SOL=zeros(1,szBin(2));

while szInactiveMatrix(1)<szBin(1)
%%
    %phase 1
    if szActiveMatrix(1)==1
        row=1;
    else
        SUM=sum(ActiveMatrix');
        row=find(SUM==min(SUM));
    end
%%   
    %phase 2
    subMatrix=ActiveMatrix(row,:);
    if length(row)==1
        SUM=subMatrix;
    else
        SUM=sum(subMatrix);
    end
    column=find(SUM==max(SUM));
%%    
    %phase 3
    subMatrix=ActiveMatrix(:,column);
    if length(column)==1
    else
       SUM=sum(subMatrix);
       column=column(find(SUM==max(SUM)));
    end
%%    
    %phase 4
    if length(column)>1
        if isempty(InactiveMatrix)
           SOL(column(1))=1;
           col=column(1);
        else
           subMatrix=InactiveMatrix(:,column);
           if szInactiveMatrix(1)==1
               SUM=subMatrix;
           else
               SUM=sum(subMatrix);
           end
           column=column(find(SUM==min(SUM)));
           SOL(column(1))=1;
           col=column(1);
        end        
    else
        SOL(column)=1;
        col=column;
    end
%%   
    %phase 5   
    tempActiveMatrix=ActiveMatrix;
    for i=1:szActiveMatrix(1)
        if ActiveMatrix(i,col)==1
            InactiveMatrix(end+1,:)=ActiveMatrix(i,:);
            tempActiveMatrix(i,:)=-1;
        else
        end        
    end
    rowindex=find(tempActiveMatrix(:,1)~=-1);
    ActiveMatrix=tempActiveMatrix(rowindex,:);
    
    szInactiveMatrix=size(InactiveMatrix);
    szActiveMatrix=size(ActiveMatrix);
%%   
end

        