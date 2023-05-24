function Rule=MultiClass(DatasetName)

%save final result;
total=0;
%read data;
[attribute,Data]=read(DatasetName);
%%
%get classes;
decision=Data(:,end);
class=unique(decision);
%%
%CLIP4
NoiseThreshold=0;
StopThreshold=0;

for m=1:length(class)
    T=class(m); 

    ResultNum=0;
    %get POS and NEG
    NumPos=0;
    NumNeg=0;
    POS={};
    NEG={};
    for j=1:length(decision)
        if isequal(decision(j),T)
           if ~in(POS,Data(j,1:end-1))
              POS(NumPos+1,:)=Data(j,1:end-1);
              NumPos=NumPos+1;
           end
        else
           if ~in(NEG,Data(j,1:end-1))
              NEG(NumNeg+1,:)=Data(j,1:end-1);
              NumNeg=NumNeg+1;
           end
        end
    end
%%  %phase 1    
    %initialization
    N_pre=1;
    POS_vector{1}=POS;
    K=length(attribute)-1;
    
    for j=1:NumNeg
        %get all SOL Matrix in j level;
        neg=NEG(j,:);        
        for p=1:N_pre
            Bin=[];
            for k=1:K
                sz=size(POS_vector{p});                
                for q=1:sz(1)                    
                    if isequal(POS_vector{p}{q,k},neg{k})
                       Bin(q,k)=0;
                    end                    
                    if ~isequal(POS_vector{p}{q,k},neg{k})
                       Bin(q,k)=1;
                    end                    
                    if isequal(POS_vector{p}{q,k},'*')
                       Bin(q,k)=0;
                    end                    
                    if isequal(neg{k},'*')
                       Bin(q,k)=0;
                    end            
                end
            end
            SOLMatrix(p,:)=SolveSCProblem(Bin);
        end
        %get all POS matrix in j level
        N_now=0;
        temp_POS_vector=[];
        for p=1:N_pre          
            for k=1:K
                if SOLMatrix(p,k)==1
                   count=1;
                   sz=size(POS_vector{p});
                   N_now=N_now+1;
                   for q=1:sz(1)
                       if ~isequal(POS_vector{p}{q,k},neg{k})
                          temp_POS_vector{N_now}(count,:)=POS_vector{p}(q,:);
                          count=count+1;
                       end
                   end                    
                end                
            end             
        end  
        %exclude noisy POS in j level;
        POS_vector=temp_POS_vector;
        temp_N_now=N_now;
        for p=1:temp_N_now
            sz=size(POS_vector{p});
            if sz(1)<NoiseThreshold
               POS_vector{p}=[];
               N_now=N_now-1;
            end
        end
        [POS_vector,N_now]=EliminateRedundantMatrices(POS_vector);
        N_pre=N_now;        
    end   
%% %phase 2    
    %create Bin matrix;
    BIN=zeros(NumPos,N_now);
    for p=1:N_now
        for j=1:NumPos
            sz=size(POS_vector{p});
            for q=1:sz(1)
                if isequal(POS(j,:),POS_vector{p}(q,:))
                   BIN(j,p)=1;
                end
            end
        end
    end    
    SOL=SolveSCProblem(BIN);
    RuleNum=0;
    %generate rules
    for p=1:N_now
        %perform back projection
        subBIN=NEG;
        if SOL(p)==1
           for k=1:K
               for q=1:NumNeg
                   if isequal(NEG(q,k),'*')
                      subBIN(q,k)=0;
                   else
                      sz=size(POS_vector{p});
                      for m=1:sz(1)
                          if isequal(POS_vector{p}(m,k),NEG(q,k))
                             subBIN{q,k}=0;
                          end
                      end                        
                   end                    
               end
           end        
           %change non_1 valule to 1 value
           for k=1:K
               for q=1:NumNeg
                   if ~isequal(subBIN{q,k},0)
                      subBIN{q,k}=1;
                   end
               end
           end        
           subBIN=cell2mat(subBIN);
           subSOL=SolveSCProblem(subBIN);
           %produce rule
           Rule{RuleNum+1}={};
           count=1;
           for k=1:K
               if subSOL(k)==1
                  num=1;
                  subColumn={};
                  for q=1:NumNeg
                      if subBIN(q,k)==1
                         subColumn(num)=NEG(q,k);
                         num=num+1;                    
                      end
                  end                
                  subColumn=unique(subColumn);
                  sz=size(subColumn);                
                  for j=1:sz(2)
                      Rule{RuleNum+1}{count}=[{k},subColumn(j)];
                      count=count+1;
                  end
               end
           end
           RuleNum=RuleNum+1;              
        end
    end
%% %phase 3    
    best_covered=0;
    previous_best_covered=0;
    BestRule=[];
    result=[];
    
    while best_covered>=0
          sz1=size(Rule);
          best_covered=0;
          %find the best rule
          for i=1:sz1(2)
              cover=NumPos;
              for j=1:NumPos
                  for k=1:K
                      sz2=size(Rule{i}); 
                      sign=0;
                      for q=1:sz2(2)
                         if k==Rule{i}{q}{1} && isequal(Rule{i}{q}{2},POS{j,k})
                            cover=cover-1;                            
                            sign=1;
                            break;
                         end
                      end
                      if sign==1
                         break;
                      end
                  end
              end            
              if best_covered<cover
                 best_covered=cover;
                 BestRule=Rule{i};                
              end
          end
      
          %save final result;
          result{ResultNum+1}=BestRule;
          ResultNum=ResultNum+1;

          %stop criterion
          NumPos=NumPos-best_covered;
          if NumPos<StopThreshold || NumPos==0
             break;
          end
          %exclude examples covered by the best rule
          sz=size(BestRule); 
          tempPOS=POS;
          for i=1:NumPos+best_covered
              count=0;            
              for q=1:sz(2)               
                  if ~isequal(BestRule{q}{2},POS{i,BestRule{q}{1}})
                     count=count+1;
                  end                
              end            
              if count==sz(2)
                 tempPOS(i,:)={0};
              end
          end        
          temp=tempPOS(:,1);
          count=1;
          row=[];
          for i=1:NumPos+best_covered
              if isequal(temp(i),{0})
              else
                  row(count)=i;
                  count=count+1;
              end
          end        
          POS=tempPOS(row,:);
          %stop criterion;        
          if best_covered<uint8(previous_best_covered/2) && best_covered<uint8(NumPos/2)
             best_covered=-1;
          end
          %assign new value;
          previous_best_covered=best_covered;
    end   
%% %format output
    for i=1:ResultNum 
        sz=size(result{i});    
        tempRule=[];
        for j=1:sz(2)
            if j<sz(2)
               tempRule=[tempRule,attribute{result{i}{j}{1}},'#',result{i}{j}{2},' ','AND',' ']; 
            else if j==sz(2)
                    tempRule=[tempRule,attribute{result{i}{j}{1}},'#',result{i}{j}{2}];
                    tempRule=['IF',' ',tempRule,' ','THEN',' ',attribute{end},'=',T{1}];
                end
            end
        end
        finalRule{total+1}=tempRule;
        total=total+1;
    end
end
Rule=finalRule;